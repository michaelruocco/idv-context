package uk.co.idv.identity.entities.phonenumber;

import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataItems;

public class PhoneNumbersOnly extends RequestedData {

    public PhoneNumbersOnly() {
        super(RequestedDataItems.PHONE_NUMBERS);
    }

}
