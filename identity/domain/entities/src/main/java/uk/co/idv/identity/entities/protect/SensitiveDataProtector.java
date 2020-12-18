package uk.co.idv.identity.entities.protect;

import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

public interface SensitiveDataProtector {

    Identity protect(Identity identity);

    EmailAddresses protect(EmailAddresses emailAddresses);

    PhoneNumbers protect(PhoneNumbers phoneNumbers);

}
