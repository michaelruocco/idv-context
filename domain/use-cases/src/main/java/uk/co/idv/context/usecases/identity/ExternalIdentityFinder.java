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
public class ExternalIdentityFinder implements IdentityFinder {

    private final AliasLoader aliasLoader;
    private final AsyncDataLoader dataLoader;

    @Override
    public Identity find(FindIdentityRequest request) {
        Aliases aliases = loadAliases(request);
        FindIdentityRequest updatedRequest = request.setAliases(aliases);
        DataFutures futures = dataLoader.loadData(updatedRequest);
        return Identity.builder()
                .aliases(updatedRequest.getAliases())
                .country(updatedRequest.getCountry())
                .phoneNumbers(futures.getPhoneNumbersNow())
                .emailAddresses(futures.getEmailAddressesNow())
                .build();
    }

    private Aliases loadAliases(FindIdentityRequest request) {
        Aliases loadedAliases = aliasLoader.load(request);
        return loadedAliases.add(request.getProvidedAlias());
    }

}
