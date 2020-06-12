package uk.co.idv.context.adapter.stub.identity.data;

import lombok.Builder;
import uk.co.idv.context.adapter.stub.identity.data.alias.StubAliasSupplier;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;
import uk.co.idv.context.adapter.stub.identity.data.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.context.adapter.stub.identity.data.emailaddress.StubEmailAddressSupplier;

import java.util.function.Supplier;

@Builder
public class StubDataSupplierFactory implements DataSupplierFactory {

    private final Delay aliasDelay;
    private final Delay phoneNumberDelay;
    private final Delay emailAddressDelay;

    @Override
    public Supplier<Aliases> aliasesSupplier(FindIdentityRequest request) {
        return new StubAliasSupplier(request, aliasDelay);
    }

    @Override
    public Supplier<PhoneNumbers> phoneNumberSupplier(FindIdentityRequest request) {
        return new StubPhoneNumberSupplier(request, phoneNumberDelay);
    }

    @Override
    public Supplier<EmailAddresses> emailAddressSupplier(FindIdentityRequest request) {
        return new StubEmailAddressSupplier(request, emailAddressDelay);
    }

}
