package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.data.AliasLoader;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataFutures;

@Builder
@Slf4j
public class ExternalIdentityLoader implements IdentityLoader {

    private final AliasLoader aliasLoader;
    private final AsyncDataLoader dataLoader;

    @Override
    public Identity load(LoadIdentityRequest request) {
        Aliases aliases = loadAliases(request);
        LoadIdentityRequest updatedRequest = request.setAliases(aliases);
        DataFutures futures = dataLoader.loadData(updatedRequest);
        System.out.println("futures " + futures);
        return Identity.builder()
                .aliases(updatedRequest.getAliases())
                .country(updatedRequest.getCountry())
                .phoneNumbers(futures.getPhoneNumbersNow())
                .emailAddresses(futures.getEmailAddressesNow())
                .build();
    }

    private Aliases loadAliases(LoadIdentityRequest request) {
        Aliases loadedAliases = aliasLoader.load(request);
        return loadedAliases.add(request.getProvidedAlias());
    }

}
