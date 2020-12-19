package uk.co.idv.identity.entities.channel.provideddata;

import uk.co.idv.identity.entities.emailaddress.EmailAddresses;

public interface EmailAddressesProvider {

    default EmailAddresses getEmailAddresses() {
        return new EmailAddresses();
    }

}
