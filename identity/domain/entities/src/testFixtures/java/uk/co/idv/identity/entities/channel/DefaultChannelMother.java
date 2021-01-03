package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

public interface DefaultChannelMother {

    static DefaultChannel withId(String id) {
        return builder().id(id).build();
    }

    static DefaultChannel withCountry(CountryCode country) {
        return builder().country(country).build();
    }

    static DefaultChannel withPhoneNumbers(PhoneNumber... phoneNumbers) {
        return builder().phoneNumbers(PhoneNumbersMother.with(phoneNumbers)).build();
    }

    static DefaultChannel withPhoneNumbers(PhoneNumbers phoneNumbers) {
        return builder().phoneNumbers(phoneNumbers).build();
    }

    static DefaultChannel withEmailAddresses(EmailAddress... emailAddresses) {
        return builder().emailAddresses(EmailAddressesMother.with(emailAddresses)).build();
    }

    static DefaultChannel withEmailAddresses(EmailAddresses emailAddresses) {
        return builder().emailAddresses(emailAddresses).build();
    }

    static DefaultChannel withData() {
        return builder()
                .phoneNumbers(PhoneNumbersMother.one())
                .emailAddresses(EmailAddressesMother.one())
                .build();
    }

    static DefaultChannel build() {
        return builder().build();
    }

    static DefaultChannel.DefaultChannelBuilder builder() {
        return DefaultChannel.builder()
                .id("default-channel")
                .country(CountryCode.GB)
                .phoneNumbers(PhoneNumbersMother.empty())
                .emailAddresses(EmailAddressesMother.empty());
    }

}
