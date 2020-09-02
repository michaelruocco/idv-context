package uk.co.idv.context.adapter.json.phonenumber;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.Arrays;

public class PhoneNumberModule extends SimpleModule {

    public PhoneNumberModule() {
        super("phone-number-module", Version.unknownVersion());
        setUpPhoneNumber();
        setUpPhoneNumbers();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule()
        );
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
