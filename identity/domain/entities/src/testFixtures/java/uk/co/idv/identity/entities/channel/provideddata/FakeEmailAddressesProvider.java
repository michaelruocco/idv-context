package uk.co.idv.identity.entities.channel.provideddata;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;

@RequiredArgsConstructor
public class FakeEmailAddressesProvider implements EmailAddressesProvider {

    private final EmailAddresses emailAddresses;

    @Override
    public EmailAddresses getEmailAddresses() {
        return emailAddresses;
    }

}
