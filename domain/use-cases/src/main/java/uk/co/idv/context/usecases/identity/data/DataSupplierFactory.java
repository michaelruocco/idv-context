package uk.co.idv.context.usecases.identity.data;

import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;

import java.util.function.Supplier;

public interface DataSupplierFactory {

    Supplier<PhoneNumbers> phoneNumberSupplier(LoadIdentityRequest request);

    Supplier<EmailAddresses> emailAddressSupplier(LoadIdentityRequest request);

}
