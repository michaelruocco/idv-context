package uk.co.idv.identity.adapter.eligibility.external;

import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.identity.adapter.eligibility.external.data.StubDataSupplierFactory;
import uk.co.idv.identity.adapter.eligibility.external.data.alias.StubAliasLoader;
import uk.co.idv.identity.usecases.eligibility.external.ExternalFindIdentity;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoader;
import uk.co.idv.identity.usecases.eligibility.external.data.DataSupplierFactory;
import uk.co.idv.identity.usecases.eligibility.external.data.FindIdentityRequestConverter;

public class ExternalFindIdentityStub {

    private ExternalFindIdentityStub() {
        // factory methods only
    }

    public static ExternalFindIdentity build(ExternalFindIdentityStubConfig config) {
        return ExternalFindIdentity.builder()
                .converter(new FindIdentityRequestConverter(config))
                .aliasLoader(new StubAliasLoader())
                .dataLoader(toAsyncDataLoader(config))
                .build();
    }

    private static AsyncDataLoader toAsyncDataLoader(ExternalFindIdentityStubConfig config) {
        return AsyncDataLoader.builder()
                .executor(config.getExecutor())
                .supplierFactory(toSupplierFactory(config))
                .futureWaiter(new FutureWaiter())
                .build();
    }

    private static DataSupplierFactory toSupplierFactory(ExternalFindIdentityStubConfig config) {
        return StubDataSupplierFactory.builder()
                .phoneNumberDelay(config.getPhoneNumberDelay())
                .emailAddressDelay(config.getEmailAddressDelay())
                .build();
    }

}
