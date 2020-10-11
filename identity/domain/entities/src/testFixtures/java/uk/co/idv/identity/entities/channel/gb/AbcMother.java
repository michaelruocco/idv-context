package uk.co.idv.identity.entities.channel.gb;

import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

public interface AbcMother {

    static Abc abc() {
        return builder().build();
    }

    static Object withData() {
        return builder()
                .phoneNumbers(PhoneNumbersMother.two())
                .emailAddresses(EmailAddressesMother.two())
                .build();
    }

    static Abc.AbcBuilder builder() {
        return Abc.builder();
    }

}
