package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

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

    static Identity withPhoneNumbers(PhoneNumber... phoneNumbers) {
        return withPhoneNumbers(PhoneNumbersMother.with(phoneNumbers));
    }

    static Identity withPhoneNumbers(PhoneNumbers phoneNumbers) {
        return exampleBuilder().phoneNumbers(phoneNumbers).build();
    }

    static Identity withEmailAddresses(EmailAddresses emailAddresses) {
        return exampleBuilder().emailAddresses(emailAddresses).build();
    }

    static Identity example() {
        return exampleBuilder().build();
    }

    static Identity minimal() {
        return emptyBuilder()
                .aliases(AliasesMother.idvIdOnly())
                .build();
    }

    static Identity withoutIdvId() {
        return exampleBuilder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .build();
    }

    static Identity example1() {
        return exampleBuilder()
                .aliases(AliasesMother.idvIdAndDebitCardNumber1())
                .phoneNumbers(PhoneNumbersMother.mobile())
                .emailAddresses(EmailAddressesMother.one())
                .build();
    }

    static Identity.IdentityBuilder exampleBuilder() {
        return Identity.builder()
                .country(CountryCode.GB)
                .aliases(AliasesMother.idvIdAndCreditCardNumber())
                .phoneNumbers(PhoneNumbersMother.mobileAndOther())
                .emailAddresses(EmailAddressesMother.two());
    }

    static Identity.IdentityBuilder emptyBuilder() {
        return Identity.builder()
                .country(CountryCode.GB)
                .aliases(AliasesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .emailAddresses(EmailAddressesMother.empty());
    }

}
