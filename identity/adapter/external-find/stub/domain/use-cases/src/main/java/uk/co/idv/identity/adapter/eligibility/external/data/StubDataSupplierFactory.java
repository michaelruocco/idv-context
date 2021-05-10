package uk.co.idv.identity.adapter.eligibility.external.data;

import lombok.Builder;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.identity.adapter.eligibility.external.data.emailaddress.StubEmailAddressSupplier;
import uk.co.idv.identity.adapter.eligibility.external.data.mobiledevice.StubMobileDeviceSupplier;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.usecases.eligibility.external.data.AsyncDataLoadRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.DataSupplierFactory;
import uk.co.idv.identity.adapter.eligibility.external.data.phonenumber.StubPhoneNumberSupplier;

import java.time.Duration;
import java.util.function.Supplier;

@Builder
public class StubDataSupplierFactory implements DataSupplierFactory {

    private final Duration phoneNumberDelay;
    private final Duration emailAddressDelay;
    private final Duration mobileDevicesDelay;

    @Override
    public Supplier<PhoneNumbers> phoneNumberSupplier(AsyncDataLoadRequest request) {
        return new StubPhoneNumberSupplier(request, new Delay(phoneNumberDelay));
    }

    @Override
    public Supplier<EmailAddresses> emailAddressSupplier(AsyncDataLoadRequest request) {
        return new StubEmailAddressSupplier(request, new Delay(emailAddressDelay));
    }

    @Override
    public Supplier<MobileDevices> mobileDeviceSupplier(AsyncDataLoadRequest request) {
        return new StubMobileDeviceSupplier(request, new Delay(mobileDevicesDelay));
    }

}
