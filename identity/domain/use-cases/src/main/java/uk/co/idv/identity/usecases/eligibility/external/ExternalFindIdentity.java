package uk.co.idv.identity.usecases.eligibility.external;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.DefaultIdentity;
import uk.co.idv.identity.usecases.eligibility.external.data.AliasLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataFutures;
import uk.co.idv.identity.usecases.eligibility.external.data.FindIdentityRequestConverter;

@Builder
@RequiredArgsConstructor
@Slf4j
public class ExternalFindIdentity {

    private final FindIdentityRequestConverter converter;
    private final AliasLoader aliasLoader;
    private final AsyncDataLoader dataLoader;

    public Identity find(FindIdentityRequest request) {
        Aliases aliases = loadAliases(request);
        AsyncDataLoadRequest loadRequest = converter.toAsyncDataLoadRequest(aliases, request);
        DataFutures futures = dataLoader.loadData(loadRequest);
        return DefaultIdentity.builder()
                .aliases(aliases)
                .country(request.getCountry())
                .phoneNumbers(futures.getPhoneNumbersNow())
                .emailAddresses(futures.getEmailAddressesNow())
                .mobileDevices(futures.getMobileDevicesNow())
                .build();
    }

    private Aliases loadAliases(FindIdentityRequest request) {
        Aliases loadedAliases = aliasLoader.load(request);
        log.debug("loaded aliases {}", loadedAliases);
        return loadedAliases.add(request.getAliases());
    }

}
