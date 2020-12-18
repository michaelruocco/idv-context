package uk.co.idv.identity.entities.channel.provideddata;

import uk.co.idv.identity.entities.protect.SensitiveDataProtector;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

public interface PhoneNumbersProvider {

    PhoneNumbers getPhoneNumbers();

    default PhoneNumbers getProtectedPhoneNumbers(SensitiveDataProtector masker) {
        return masker.protect(getPhoneNumbers());
    }

}
