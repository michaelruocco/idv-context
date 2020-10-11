package uk.co.idv.identity.entities.channel.provideddata;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.Optional;

@RequiredArgsConstructor
public class ProvidedPhoneNumbersAppender implements DataAppender{

    private final Channel channel;

    @Override
    public Identity apply(Identity identity) {
        return addProvidedPhoneNumbersIfPresent(identity.getPhoneNumbers())
                .map(identity::withPhoneNumbers)
                .orElse(identity);
    }

    private Optional<PhoneNumbers> addProvidedPhoneNumbersIfPresent(PhoneNumbers phoneNumbers) {
        if (channel instanceof PhoneNumbersProvider) {
            PhoneNumbersProvider provider = (PhoneNumbersProvider) channel;
            return Optional.of(phoneNumbers.add(provider.getPhoneNumbers()));
        }
        return Optional.empty();
    }

}
