package uk.co.idv.context.adapter.identity.data.stub;

import uk.co.idv.context.adapter.identity.data.stub.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.context.adapter.identity.data.stub.phonenumber.StubPhoneNumberSupplier;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;

import java.util.function.Supplier;

import static uk.co.idv.context.adapter.identity.data.stub.SystemPropertyLoader.loadDelay;

public class StubDataSupplierFactory implements DataSupplierFactory {

    public Supplier<PhoneNumbers> phoneNumberSupplier(FindIdentityRequest request) {
        return new StubPhoneNumberSupplier(request, phoneNumberDelay());
    }

    public Supplier<EmailAddresses> emailAddressSupplier(FindIdentityRequest request) {
        return new StubEmailAddressSupplier(request, emailDelay());
    }

    private static Delay phoneNumberDelay() {
        return loadDelay("stubbed.phone.number.load.delay", 500);
    }

    private static Delay emailDelay() {
        return loadDelay("stubbed.email.address.load.delay", 1500);
    }

}
