package uk.co.idv.context.adapter.stub.identity.find;

import uk.co.idv.context.adapter.stub.identity.find.data.StubDataSupplierFactory;
import uk.co.idv.context.usecases.identity.find.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.find.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.find.data.DataSupplierFactory;

public class StubFindIdentity extends ExternalFindIdentity {

    public StubFindIdentity(StubFindIdentityConfig config) {
        this(config, toSupplierFactory(config));
    }

    public StubFindIdentity(StubFindIdentityConfig config, DataSupplierFactory factory) {
        super(factory, new AsyncDataLoader(config.getExecutor(), factory));
    }

    private static DataSupplierFactory toSupplierFactory(final StubFindIdentityConfig config) {
        return StubDataSupplierFactory.builder()
                .aliasDelay(config.getAliasDelay())
                .phoneNumberDelay(config.getPhoneNumberDelay())
                .emailAddressDelay(config.getEmailAddressDelay())
                .build();
    }

}
