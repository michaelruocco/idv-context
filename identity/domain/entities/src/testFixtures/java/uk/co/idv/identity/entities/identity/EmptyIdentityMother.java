package uk.co.idv.identity.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

public interface EmptyIdentityMother {

    static DefaultIdentity.DefaultIdentityBuilder builder() {
        return DefaultIdentity.builder()
                .country(CountryCode.GB)
                .aliases(AliasesMother.empty())
                .phoneNumbers(PhoneNumbersMother.empty())
                .emailAddresses(EmailAddressesMother.empty())
                .mobileDevices(MobileDevicesMother.empty());
    }

}
