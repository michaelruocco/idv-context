package uk.co.idv.context.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.eligibility.EligibilityMother;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeCreateEligibilityTest {

    private final ChannelCreateEligibility channelCreate1 = buildCreateForChannels("my-channel-1");
    private final ChannelCreateEligibility channelCreate2 = buildCreateForChannels("my-channel-2");

    private final CompositeCreateEligibility compositeCreate = new CompositeCreateEligibility(
            channelCreate1,
            channelCreate2
    );

    @Test
    void shouldPerformUpdateForGivenChannelId() {
        Channel channel = DefaultChannelMother.withId("my-channel-1");
        CreateEligibilityRequest request = CreateEligibilityRequestMother.withChannel(channel);
        Eligibility expectedEligibility = EligibilityMother.build();
        given(channelCreate1.create(request)).willReturn(expectedEligibility);

        Eligibility eligibility = compositeCreate.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    @Test
    void shouldThrowExceptionIfUpdateNotConfiguredForChannelId() {
        String channelId = "not-configured-channel";
        Channel channel = DefaultChannelMother.withId(channelId);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        Throwable error = catchThrowable(() -> compositeCreate.create(request));

        assertThat(error)
                .isInstanceOf(CreateEligibilityNotConfiguredException.class)
                .hasMessage(channelId);
    }

    private ChannelCreateEligibility buildCreateForChannels(String... channelIds) {
        ChannelCreateEligibility update = mock(ChannelCreateEligibility.class);
        given(update.getSupportedChannelIds()).willReturn(Arrays.asList(channelIds));
        return update;
    }

}
