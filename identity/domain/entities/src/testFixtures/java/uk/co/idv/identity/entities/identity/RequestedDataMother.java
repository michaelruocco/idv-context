package uk.co.idv.identity.entities.identity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public interface RequestedDataMother {

    static RequestedData allRequested() {
        return with(RequestedData.allItems());
    }

    static RequestedData noneRequested() {
        return with(Collections.emptyList());
    }

    static RequestedData onlyEmailAddresses() {
        return RequestedData.emailAddressesOnly();
    }

    static RequestedData onlyPhoneNumbers() {
        return RequestedData.phoneNumbersOnly();
    }

    static RequestedData with(String... items) {
        return with(Arrays.asList(items));
    }

    static RequestedData with(Collection<String> items) {
        return new RequestedData(items);
    }

}
