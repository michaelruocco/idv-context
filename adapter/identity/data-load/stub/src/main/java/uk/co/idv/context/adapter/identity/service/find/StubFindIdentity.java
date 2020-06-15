package uk.co.idv.context.adapter.identity.service.find;

import uk.co.idv.context.adapter.identity.service.find.data.StubDataSupplierFactory;
import uk.co.idv.context.adapter.identity.service.find.data.alias.StubAliasLoader;
import uk.co.idv.context.usecases.identity.service.find.ExternalFindIdentity;
import uk.co.idv.context.usecases.identity.service.find.data.AsyncDataLoader;
import uk.co.idv.context.usecases.identity.service.find.data.DataSupplierFactory;
import uk.co.idv.context.usecases.identity.service.find.data.FindIdentityRequestConverter;

public class StubFindIdentity {

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
