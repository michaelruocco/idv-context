package uk.co.idv.identity.entities.channel.provideddata;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.Optional;

@RequiredArgsConstructor
public class ProvidedEmailAddressesAppender implements DataAppender {

    private final Channel channel;

    @Override
    public Identity apply(Identity identity) {
        return addProvidedEmailAddressesIfPresent(identity.getEmailAddresses())
                .map(identity::withEmailAddresses)
                .orElse(identity);
    }

    private Optional<EmailAddresses> addProvidedEmailAddressesIfPresent(EmailAddresses emailAddresses) {
        if (channel instanceof EmailAddressesProvider) {
            EmailAddressesProvider provider = (EmailAddressesProvider) channel;
            return Optional.of(emailAddresses.add(provider.getEmailAddresses()));
        }
        return Optional.empty();
    }

}
