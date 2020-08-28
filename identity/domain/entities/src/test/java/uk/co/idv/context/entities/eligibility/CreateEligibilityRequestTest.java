package uk.co.idv.context.entities.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.RequestedData;
import uk.co.idv.context.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;

class CreateEligibilityRequestTest {

    @Test
    void shouldReturnAliases() {
        DefaultAliases aliases = AliasesMother.idvIdAndDebitCardNumber();

        CreateEligibilityRequest request = CreateEligibilityRequestMother.withAliases(aliases);

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnChannel() {
        Channel channel = DefaultChannelMother.build();

        CreateEligibilityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        assertThat(request.getChannel()).isEqualTo(channel);
    }

    @Test
    void shouldReturnRequestedData() {
        RequestedData requested = RequestedDataMother.allRequested();

        CreateEligibilityRequest request = CreateEligibilityRequestMother.withRequestedData(requested);

        assertThat(request.getRequestedData()).isEqualTo(requested);
    }

}
