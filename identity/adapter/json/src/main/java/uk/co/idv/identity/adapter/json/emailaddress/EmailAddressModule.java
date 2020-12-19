package uk.co.idv.identity.adapter.json.emailaddress;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;

public class EmailAddressModule extends SimpleModule {

    public EmailAddressModule() {
        super("email-address-module", Version.unknownVersion());

        setMixInAnnotation(EmailAddresses.class, EmailAddressesMixin.class);
        setMixInAnnotation(EmailAddress.class, EmailAddressMixin.class);

        addDeserializer(EmailAddresses.class, new EmailAddressesDeserializer());
        addDeserializer(EmailAddress.class, new EmailAddressDeserializer());
    }

}
