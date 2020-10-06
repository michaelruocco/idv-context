package uk.co.idv.identity.adapter.eligibility.external;

import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataSupplierFactory;
import uk.co.idv.identity.adapter.eligibility.external.data.alias.StubAliasLoader;
import uk.co.idv.identity.usecases.eligibility.external.ExternalFindIdentity;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataSupplierFactory;
import uk.co.idv.identity.usecases.eligibility.external.data.FindIdentityRequestConverter;

public class StubExternalFindIdentity {

    private StubExternalFindIdentity() {
        // factory methods only
    }

    public static ExternalFindIdentity withExampleConfig() {
        return build(StubExternalFindIdentityConfig.buildExample());
    }

    public static ExternalFindIdentity build(StubExternalFindIdentityConfig config) {
        return ExternalFindIdentity.builder()
                .converter(new FindIdentityRequestConverter(config))
                .aliasLoader(new StubAliasLoader())
                .dataLoader(toAsyncDataLoader(config))
                .build();
    }

    private static AsyncDataLoader toAsyncDataLoader(StubExternalFindIdentityConfig config) {
        return AsyncDataLoader.builder()
                .executor(config.getExecutor())
                .supplierFactory(toSupplierFactory(config))
                .futureWaiter(new FutureWaiter())
                .build();
    }

    private static DataSupplierFactory toSupplierFactory(StubExternalFindIdentityConfig config) {
        return StubDataSupplierFactory.builder()
                .phoneNumberDelay(config.getPhoneNumberDelay())
                .emailAddressDelay(config.getEmailAddressDelay())
                .build();
    }

}
