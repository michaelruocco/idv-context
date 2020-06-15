package uk.co.idv.context.usecases.identity.service.find.data;

import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.util.function.Supplier;

public interface DataSupplierFactory {

    Supplier<PhoneNumbers> phoneNumberSupplier(AsyncDataLoadRequest request);

    Supplier<EmailAddresses> emailAddressSupplier(AsyncDataLoadRequest request);

}
