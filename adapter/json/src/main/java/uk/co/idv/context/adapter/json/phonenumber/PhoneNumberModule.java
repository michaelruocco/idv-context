package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

public class PhoneNumberModule extends SimpleModule {

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
