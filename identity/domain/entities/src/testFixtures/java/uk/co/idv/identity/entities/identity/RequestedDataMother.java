package uk.co.idv.identity.entities.identity;

import uk.co.idv.identity.entities.emailaddress.EmailAddressesOnly;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesOnly;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersOnly;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public interface RequestedDataMother {

    static RequestedData allRequested() {
        return with(RequestedDataItems.all());
    }

    static RequestedData noneRequested() {
        return with(Collections.emptyList());
    }

    static RequestedData emailAddressesOnly() {
        return new EmailAddressesOnly();
    }

    static RequestedData phoneNumbersOnly() {
        return new PhoneNumbersOnly();
    }

    static RequestedData mobileDevicesOnly() {
        return new MobileDevicesOnly();
    }

    static RequestedData with(String... items) {
        return with(Arrays.asList(items));
    }

    static RequestedData with(Collection<String> items) {
        return new RequestedData(items);
    }

}
