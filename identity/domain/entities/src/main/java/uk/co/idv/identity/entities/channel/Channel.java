package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.channel.provideddata.EmailAddressesProvider;
import uk.co.idv.identity.entities.channel.provideddata.MobileDevicesProvider;
import uk.co.idv.identity.entities.channel.provideddata.PhoneNumbersProvider;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.Optional;

public interface Channel extends EmailAddressesProvider, PhoneNumbersProvider, MobileDevicesProvider {

    String getId();

    CountryCode getCountry();

    default boolean hasId(String id) {
        return getId().equals(id);
    }

    default Channel withEmailAddresses(EmailAddresses emailAddresses) {
        return this;
    }

    default Channel withPhoneNumbers(PhoneNumbers phoneNumbers) {
        return this;
    }

    default Optional<Boolean> hasValidCookie() { return Optional.empty(); }

}
