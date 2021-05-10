package uk.co.idv.identity.usecases.eligibility.external.data;

import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.function.Supplier;

public interface DataSupplierFactory {

    Supplier<PhoneNumbers> phoneNumberSupplier(AsyncDataLoadRequest request);

    Supplier<EmailAddresses> emailAddressSupplier(AsyncDataLoadRequest request);

    Supplier<MobileDevices> mobileDeviceSupplier(AsyncDataLoadRequest request);

}
