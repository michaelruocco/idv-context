package uk.co.idv.context.usecases.identity.data;

import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

import java.util.function.Supplier;

public interface DataSupplierFactory {

    Supplier<PhoneNumbers> phoneNumberSupplier(FindIdentityRequest request);

    Supplier<EmailAddresses> emailAddressSupplier(FindIdentityRequest request);

}
