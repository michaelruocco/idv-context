package uk.co.idv.identity.entities.channel.provideddata;

import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.protect.SensitiveDataProtector;

public interface EmailAddressesProvider {

    EmailAddresses getEmailAddresses();

    default EmailAddresses getProtectedEmailAddresses(SensitiveDataProtector masker) {
        return masker.protect(getEmailAddresses());
    }

}
