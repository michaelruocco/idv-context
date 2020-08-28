package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

public interface EmptyIdentityMother {

    static DefaultIdentity.DefaultIdentityBuilder builder() {
        return DefaultIdentity.builder()
                .country(CountryCode.GB)
                .aliases(AliasesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .emailAddresses(EmailAddressesMother.empty());
    }

}
