package uk.co.idv.identity.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

public interface IdentityMother {

    static Identity withoutCountry() {
        return withCountry(null);
    }

    static Identity withCountry(CountryCode country) {
        return exampleBuilder().country(country).build();
    }

    static Identity withAliases(Alias... aliases) {
        return withAliases(AliasesMother.with(aliases));
    }

    static Identity withAliases(Aliases aliases) {
        return exampleBuilder().aliases(aliases).build();
    }

    static Identity withPhoneNumbers(PhoneNumber... phoneNumber) {
        return withPhoneNumbers(PhoneNumbersMother.with(phoneNumber));
    }

    static Identity withPhoneNumbers(PhoneNumbers phoneNumbers) {
        return exampleBuilder().phoneNumbers(phoneNumbers).build();
    }

    static Identity withEmailAddresses(EmailAddress... emailAddress) {
        return withEmailAddresses(EmailAddressesMother.with(emailAddress));
    }

    static Identity withEmailAddresses(EmailAddresses emailAddresses) {
        return exampleBuilder().emailAddresses(emailAddresses).build();
    }

    static Identity withMobileDevices(MobileDevice... mobileDevices) {
        return withMobileDevices(MobileDevicesMother.with(mobileDevices));
    }

    static Identity withMobileDevices(MobileDevices mobileDevices) {
        return exampleBuilder().mobileDevices(mobileDevices).build();
    }

    static Identity example() {
        return exampleBuilder().build();
    }

    static Identity withoutIdvId() {
        return exampleBuilder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .build();
    }

    static Identity example1() {
        return exampleBuilder()
                .aliases(AliasesMother.idvIdAndDebitCardNumber1())
                .phoneNumbers(PhoneNumbersMother.one())
                .emailAddresses(EmailAddressesMother.one())
                .mobileDevices(MobileDevicesMother.one())
                .build();
    }

    static DefaultIdentity.DefaultIdentityBuilder exampleBuilder() {
        return DefaultIdentity.builder()
                .country(CountryCode.GB)
                .aliases(AliasesMother.idvIdAndCreditCardNumber())
                .phoneNumbers(PhoneNumbersMother.two())
                .emailAddresses(EmailAddressesMother.two())
                .mobileDevices(MobileDevicesMother.two());
    }

}
