package uk.co.idv.context.adapter.stub.identity;

import uk.co.idv.context.adapter.stub.identity.data.StubDataSupplierFactory;
import uk.co.idv.context.usecases.identity.ExternalIdentityFinder;
import uk.co.idv.context.usecases.identity.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;

public class StubIdentityFinder extends ExternalIdentityFinder {

    public StubIdentityFinder(StubIdentityFinderConfig config) {
        this(config, toSupplierFactory(config));
    }

    public StubIdentityFinder(StubIdentityFinderConfig config, DataSupplierFactory factory) {
        super(factory, new AsyncDataLoader(config.getExecutor(), factory));
    }

    private static DataSupplierFactory toSupplierFactory(final StubIdentityFinderConfig config) {
        return StubDataSupplierFactory.builder()
                .aliasDelay(config.getAliasDelay())
                .phoneNumberDelay(config.getPhoneNumberDelay())
                .emailAddressDelay(config.getEmailAddressDelay())
                .build();
    }

}
