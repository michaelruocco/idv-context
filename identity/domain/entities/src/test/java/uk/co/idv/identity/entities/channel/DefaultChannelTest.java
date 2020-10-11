package uk.co.idv.identity.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultChannelTest {

    private static final String ID = "my-channel";

    @Test
    void shouldReturnId() {
        Channel channel = DefaultChannelMother.withId(ID);

        assertThat(channel.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnCountry() {
        CountryCode country = CountryCode.GB;

        Channel channel = DefaultChannelMother.withCountry(country);

        assertThat(channel.getCountry()).isEqualTo(country);
    }

    @Test
    void shouldReturnTrueIfIdMatches() {
        Channel channel = DefaultChannelMother.withId(ID);

        assertThat(channel.hasId(ID)).isTrue();
    }

    @Test
    void shouldReturnFalseIfIdDoesNotMatch() {
        Channel channel = DefaultChannelMother.withId(ID);

        assertThat(channel.hasId("other-id")).isFalse();
    }

    @Test
    void shouldReturnPhoneNumbers() {
        PhoneNumbers phoneNumbers = PhoneNumbersMother.one();

        DefaultChannel channel = DefaultChannelMother.withPhoneNumbers(phoneNumbers);

        assertThat(channel.getPhoneNumbers()).isEqualTo(phoneNumbers);
    }

    @Test
    void shouldReturnEmailAddress() {
        EmailAddresses emailAddresses = EmailAddressesMother.one();

        DefaultChannel channel = DefaultChannelMother.withEmailAddresses(emailAddresses);

        assertThat(channel.getEmailAddresses()).isEqualTo(emailAddresses);
    }

}
