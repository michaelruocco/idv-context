package uk.co.idv.identity.entities.channel.provideddata;

import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

public interface PhoneNumbersProvider {

    default PhoneNumbers getPhoneNumbers() {
        return new PhoneNumbers();
    }

}
