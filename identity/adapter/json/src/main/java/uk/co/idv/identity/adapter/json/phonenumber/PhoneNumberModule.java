package uk.co.idv.identity.adapter.json.phonenumber;

import com.fasterxml.jackson.core.Version;
import uk.co.idv.common.adapter.json.AbstractContextModule;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

public class PhoneNumberModule extends AbstractContextModule {

    public PhoneNumberModule() {
        super("phone-number-module", Version.unknownVersion());
        setUpPhoneNumber();
        setUpPhoneNumbers();
    }

    private void setUpPhoneNumber() {
        setMixInAnnotation(PhoneNumber.class, PhoneNumberMixin.class);
        addDeserializer(PhoneNumber.class, new PhoneNumberDeserializer());
    }

    private void setUpPhoneNumbers() {
        setMixInAnnotation(PhoneNumbers.class, PhoneNumbersMixin.class);
        addDeserializer(PhoneNumbers.class, new PhoneNumbersDeserializer());
    }

}
