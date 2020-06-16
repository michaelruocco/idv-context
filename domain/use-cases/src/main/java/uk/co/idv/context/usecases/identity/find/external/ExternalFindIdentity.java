package uk.co.idv.context.usecases.identity.find.external;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.data.AliasLoader;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoadRequest;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.find.data.DataFutures;
import uk.co.idv.context.usecases.identity.find.data.FindIdentityRequestConverter;

@Builder
@RequiredArgsConstructor
@Slf4j
public class ExternalFindIdentity {

    private final FindIdentityRequestConverter converter;
    private final AliasLoader aliasLoader;
    private final AsyncDataLoader dataLoader;

    public Identity find(ExternalFindIdentityRequest findRequest) {
        Aliases aliases = loadAliases(findRequest);
        AsyncDataLoadRequest loadRequest = converter.toAsyncDataLoadRequest(aliases, findRequest);
        DataFutures futures = dataLoader.loadData(loadRequest);
        return Identity.builder()
                .aliases(aliases)
                .country(findRequest.getCountry())
                .phoneNumbers(futures.getPhoneNumbersNow())
                .emailAddresses(futures.getEmailAddressesNow())
                .build();
    }

    private Aliases loadAliases(FindIdentityRequest request) {
        Aliases loadedAliases = aliasLoader.load(request);
        log.debug("loaded aliases {}", loadedAliases);
        return loadedAliases.add(request.getAliases());
    }

}
