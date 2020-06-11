package uk.co.idv.context.usecases.identity.data.stubbed;

import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;
import uk.co.idv.context.usecases.identity.data.DataSupplierFactory;
import uk.co.idv.context.usecases.identity.data.stubbed.emailaddress.StubbedEmailAddressSupplier;
import uk.co.idv.context.usecases.identity.data.stubbed.phonenumber.StubbedPhoneNumberSupplier;

import java.util.function.Supplier;

public class StubbedDataSupplierFactory implements DataSupplierFactory {

    public Supplier<PhoneNumbers> phoneNumberSupplier(LoadIdentityRequest request) {
        return new StubbedPhoneNumberSupplier(request);
    }

    public Supplier<EmailAddresses> emailAddressSupplier(LoadIdentityRequest request) {
        return new StubbedEmailAddressSupplier(request);
    }

}
