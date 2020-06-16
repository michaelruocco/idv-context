package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.request.UpdateIdentityRequestMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChannelUpdateIdentityTest {

    private final Collection<String> channelIds = Collections.singletonList("my-channel");
    private final UpdateIdentity update = mock(UpdateIdentity.class);

    private final ChannelUpdateIdentity channelUpdate = ChannelUpdateIdentity.builder()
            .supportedChannelIds(channelIds)
            .update(update)
            .build();

    @Test
    void shouldReturnSupportedChannelIds() {
        assertThat(channelUpdate.getSupportedChannelIds()).isEqualTo(channelIds);
    }

    @Test
    void shouldPerformUpdate() {
        UpdateIdentityRequest request = UpdateIdentityRequestMother.build();
        Identity expectedIdentity = IdentityMother.example();
        given(update.update(request)).willReturn(expectedIdentity);

        Identity identity = channelUpdate.update(request);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
