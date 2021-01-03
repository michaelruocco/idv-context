package uk.co.idv.identity.adapter.protect.mask.channel;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.adapter.protect.mask.emailaddress.EmailAddressesMasker;
import uk.co.idv.identity.adapter.protect.mask.phonenumber.PhoneNumbersMasker;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ChannelMasker implements UnaryOperator<Channel> {

    private final UnaryOperator<PhoneNumbers> phoneNumbersMasker;
    private final UnaryOperator<EmailAddresses> emailAddressesMasker;

    public ChannelMasker() {
        this(new PhoneNumbersMasker(), new EmailAddressesMasker());
    }

    @Override
    public Channel apply(Channel channel) {
        return channel
                .withPhoneNumbers(phoneNumbersMasker.apply(channel.getPhoneNumbers()))
                .withEmailAddresses(emailAddressesMasker.apply(channel.getEmailAddresses()));
    }

}
