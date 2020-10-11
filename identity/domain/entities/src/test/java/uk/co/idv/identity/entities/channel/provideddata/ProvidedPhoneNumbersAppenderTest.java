package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.channel.fake.FakeChannelMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class ProvidedPhoneNumbersAppenderTest {

    private final Identity identity = IdentityMother.withPhoneNumbers(PhoneNumbersMother.with(PhoneNumberMother.example()));

    @Test
    void shouldAppendPhoneNumbersToIdentityIfChannelProvidesPhoneNumbers() {
        PhoneNumbers providedPhoneNumbers = PhoneNumbersMother.with(PhoneNumberMother.example1());
        Channel channel = DefaultChannelMother.withPhoneNumbers(providedPhoneNumbers);
        UnaryOperator<Identity> appender = new ProvidedPhoneNumbersAppender(channel);

        Identity updated = appender.apply(identity);

        PhoneNumbers expectedPhoneNumbers = identity.getPhoneNumbers().add(providedPhoneNumbers);
        assertThat(updated.getPhoneNumbers()).containsExactlyElementsOf(expectedPhoneNumbers);
    }

    @Test
    void shouldReturnInputIdentityUnchangedIfChannelDoesNotProvidedPhoneNumbers() {
        Channel channel = FakeChannelMother.build();
        UnaryOperator<Identity> appender = new ProvidedPhoneNumbersAppender(channel);

        Identity updated = appender.apply(identity);

        assertThat(updated).isEqualTo(identity);
    }

}
