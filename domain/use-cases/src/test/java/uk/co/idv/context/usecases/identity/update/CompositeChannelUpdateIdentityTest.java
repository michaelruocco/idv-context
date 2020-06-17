package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeChannelUpdateIdentityTest {

    private final ChannelUpdateIdentity channelUpdate1 = buildUpdateForChannels("my-channel-1");
    private final ChannelUpdateIdentity channelUpdate2 = buildUpdateForChannels("my-channel-2");

    private final CompositeChannelUpdateIdentity compositeUpdate = new CompositeChannelUpdateIdentity(
            channelUpdate1,
            channelUpdate2
    );

    @Test
    void shouldPerformUpdateForGivenChannelId() {
        Channel channel = DefaultChannelMother.withId("my-channel-1");
        UpdateIdentityRequest request = UpdateIdentityRequestMother.withChannel(channel);
        Identity expectedIdentity = IdentityMother.example();
        given(channelUpdate1.update(request)).willReturn(expectedIdentity);

        Identity identity = compositeUpdate.update(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldThrowExceptionIfUpdateNotConfiguredForChannelId() {
        String channelId = "not-configured-channel";
        Channel channel = DefaultChannelMother.withId(channelId);
        UpdateIdentityRequest request = UpdateIdentityRequestMother.withChannel(channel);

        Throwable error = catchThrowable(() -> compositeUpdate.update(request));

        assertThat(error)
                .isInstanceOf(ChannelNotConfiguredForIdentityUpdateException.class)
                .hasMessage(channelId);
    }

    private ChannelUpdateIdentity buildUpdateForChannels(String... channelIds) {
        ChannelUpdateIdentity update = mock(ChannelUpdateIdentity.class);
        given(update.getSupportedChannelIds()).willReturn(Arrays.asList(channelIds));
        return update;
    }

}
