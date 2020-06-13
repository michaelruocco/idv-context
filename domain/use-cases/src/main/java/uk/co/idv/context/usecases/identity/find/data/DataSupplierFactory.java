package uk.co.idv.context.usecases.identity.find.data;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

import java.util.function.Supplier;

public interface DataSupplierFactory {

    Supplier<Aliases> aliasesSupplier(FindIdentityRequest request);

    Supplier<PhoneNumbers> phoneNumberSupplier(FindIdentityRequest request);

    Supplier<EmailAddresses> emailAddressSupplier(FindIdentityRequest request);

}
