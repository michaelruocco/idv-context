package uk.co.idv.identity.entities.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityEligibilityTest {

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        IdentityEligibility eligibility = IdentityEligibilityMother.withChannel(channel);

        assertThat(eligibility.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnAliases() {
        DefaultAliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        IdentityEligibility eligibility = IdentityEligibilityMother.withAliases(aliases);

        assertThat(eligibility.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnRequestedData() {
        RequestedData requestedData = RequestedDataMother.allRequested();

        IdentityEligibility eligibility = IdentityEligibilityMother.withRequestedData(requestedData);

        assertThat(eligibility.getRequestedData()).isEqualTo(requestedData);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = IdentityMother.example();

        IdentityEligibility eligibility = IdentityEligibilityMother.withIdentity(identity);

        assertThat(eligibility.getIdentity()).isEqualTo(identity);
    }

}
