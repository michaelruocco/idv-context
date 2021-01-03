package uk.co.idv.identity.entities.channel;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.fake.FakeChannel;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelTest {

    @Test
    void shouldReturnUnchangedChannelFromWithEmailAddressesByDefault() {
        Channel channel = new FakeChannel();

        Channel updated = channel.withEmailAddresses(EmailAddressesMother.two());

        assertThat(updated).isEqualTo(channel);
    }

    @Test
    void shouldReturnUnchangedChannelFromWithPhoneNumbersByDefault() {
        Channel channel = new FakeChannel();

        Channel updated = channel.withPhoneNumbers(PhoneNumbersMother.two());

        assertThat(updated).isEqualTo(channel);
    }

}
