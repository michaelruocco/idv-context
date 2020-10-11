package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.channel.fake.FakeChannelMother;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class ProvidedEmailAddressesAppenderTest {

    private final Identity identity = IdentityMother.withEmailAddresses(EmailAddressesMother.with("joe.bloggs@hotmail.com"));

    @Test
    void shouldAppendEmailAddressToIdentityIfChannelProvidesEmailAddresses() {
        EmailAddresses providedEmailAddresses = EmailAddressesMother.with("bugs.bunny@sky.co.uk");
        Channel channel = DefaultChannelMother.withEmailAddresses(providedEmailAddresses);
        UnaryOperator<Identity> appender = new ProvidedEmailAddressesAppender(channel);

        Identity updated = appender.apply(identity);

        EmailAddresses expectedEmailAddresses = identity.getEmailAddresses().add(providedEmailAddresses);
        assertThat(updated.getEmailAddresses()).containsExactlyElementsOf(expectedEmailAddresses);
    }

    @Test
    void shouldReturnInputIdentityUnchangedIfChannelDoesNotProvidedEmailAddresses() {
        Channel channel = FakeChannelMother.build();
        UnaryOperator<Identity> appender = new ProvidedEmailAddressesAppender(channel);

        Identity updated = appender.apply(identity);

        assertThat(updated).isEqualTo(identity);
    }

}
