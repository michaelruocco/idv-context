package uk.co.idv.context.entities.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityTest {

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        Eligibility eligibility = EligibilityMother.withChannel(channel);

        assertThat(eligibility.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnAliases() {
        Aliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        Eligibility eligibility = EligibilityMother.withAliases(aliases);

        assertThat(eligibility.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnRequested() {
        Collection<String> requested = EligibilityMother.allRequested();

        Eligibility eligibility = EligibilityMother.withRequested(requested);

        assertThat(eligibility.getRequested()).isEqualTo(requested);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        Eligibility eligibility = EligibilityMother.withIdentity(identity);

        assertThat(eligibility.getIdentity()).isEqualTo(identity);
    }

}
