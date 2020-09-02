package uk.co.idv.identity.entities.identity;

import java.util.Collections;

public interface RequestedDataMother {

    static RequestedData allRequested() {
        return new RequestedData(RequestedData.allItems());
    }

    static RequestedData noneRequested() {
        return new RequestedData(Collections.emptyList());
    }

    static RequestedData onlyEmailAddressesRequested() {
        return new RequestedData(RequestedData.EMAIL_ADDRESSES);
    }

    static RequestedData onlyPhoneNumbersRequested() {
        return new RequestedData(RequestedData.PHONE_NUMBERS);
    }

}
