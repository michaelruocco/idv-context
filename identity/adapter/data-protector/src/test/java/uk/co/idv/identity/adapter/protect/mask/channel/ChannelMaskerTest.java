package uk.co.idv.identity.adapter.protect.mask.channel;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelMaskerTest {

    private final UnaryOperator<Channel> masker = new ChannelMasker();

    @Test
    void shouldMaskPhoneNumbers() {
        PhoneNumber number1 = PhoneNumberMother.example();
        PhoneNumber number2 = PhoneNumberMother.example1();
        Channel channel = DefaultChannelMother.withPhoneNumbers(number1, number2)
                .withEmailAddresses(EmailAddressesMother.empty());

        Channel maskedChannel = masker.apply(channel);

        assertThat(maskedChannel)
                .usingRecursiveComparison()
                .ignoringFields("phoneNumbers")
                .isEqualTo(channel);
        assertThat(maskedChannel.getPhoneNumbers()).containsExactly(
                number1.withValue("**********111"),
                number2.withValue("**********212")
        );
    }

    @Test
    void shouldMaskEmailAddresses() {
        EmailAddress address1 = EmailAddressMother.joeBloggsHotmail();
        EmailAddress address2 = EmailAddressMother.bugsBunny();
        Channel channel = DefaultChannelMother.withEmailAddresses(address1, address2)
                .withPhoneNumbers(PhoneNumbersMother.empty());

        Channel maskedChannel = masker.apply(channel);

        assertThat(maskedChannel)
                .usingRecursiveComparison()
                .ignoringFields("emailAddresses")
                .isEqualTo(channel);
        assertThat(maskedChannel.getEmailAddresses()).containsExactly(
                address1.withValue("joe.**************.co.uk"),
                address2.withValue("bugs**********.co.uk")
        );
    }

}
