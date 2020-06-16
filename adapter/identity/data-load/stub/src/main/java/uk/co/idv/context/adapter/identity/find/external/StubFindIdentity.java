package uk.co.idv.context.adapter.identity.find.external;

import uk.co.idv.context.adapter.identity.find.external.data.StubDataSupplierFactory;
import uk.co.idv.context.adapter.identity.find.external.data.alias.StubAliasLoader;
import uk.co.idv.context.usecases.identity.find.external.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.find.data.DataSupplierFactory;
import uk.co.idv.context.usecases.identity.find.data.FindIdentityRequestConverter;

public class StubFindIdentity {

    private StubFindIdentity() {
        // factory method only
    }

    public static ExternalFindIdentity build(StubFindIdentityConfig config) {
        return ExternalFindIdentity.builder()
                .converter(new FindIdentityRequestConverter(config))
                .aliasLoader(new StubAliasLoader())
                .dataLoader(new AsyncDataLoader(config.getExecutor(), toSupplierFactory(config)))
                .build();
    }

    private static DataSupplierFactory toSupplierFactory(final StubFindIdentityConfig config) {
        return StubDataSupplierFactory.builder()
                .phoneNumberDelay(config.getPhoneNumberDelay())
                .emailAddressDelay(config.getEmailAddressDelay())
                .build();
    }

}
