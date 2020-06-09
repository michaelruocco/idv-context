package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

public interface IdentityMother {

    static Identity withCountry(CountryCode country) {
        return builder().country(country).build();
    }

    static Identity withAliases(Alias... aliases) {
        return withAliases(AliasesMother.with(aliases));
    }

    static Identity withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static Identity withPhoneNumbers(PhoneNumber... phoneNumbers) {
        return withPhoneNumbers(PhoneNumbersMother.with(phoneNumbers));
    }

    static Identity withPhoneNumbers(PhoneNumbers phoneNumbers) {
        return builder().phoneNumbers(phoneNumbers).build();
    }

    static Identity withEmailAddresses(EmailAddresses emailAddresses) {
        return builder().emailAddresses(emailAddresses).build();
    }

    static Identity.IdentityBuilder builder() {
        return Identity.builder()
                .country(CountryCode.GB)
                .aliases(AliasesMother.idvIdAndCreditCardNumber())
                .phoneNumbers(PhoneNumbersMother.mobileAndOther())
                .emailAddresses(EmailAddressesMother.two());
    }

}
