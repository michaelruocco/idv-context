package uk.co.idv.identity.entities.identity;

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

    static RequestedData with(String... items) {
        return with(Arrays.asList(items));
    }

    static RequestedData with(Collection<String> items) {
        return new RequestedData(items);
    }

}
