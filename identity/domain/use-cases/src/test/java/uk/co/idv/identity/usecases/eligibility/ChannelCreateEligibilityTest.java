package uk.co.idv.identity.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.eligibility.IdentityEligibilityMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChannelCreateEligibilityTest {

    private static final String SUPPORTED_CHANNEL_ID = "default-channel";

    private final CreateEligibility create = mock(CreateEligibility.class);
    private final Collection<String> channelIds = Collections.singletonList(SUPPORTED_CHANNEL_ID);

    private final ChannelCreateEligibility channelCreate = ChannelCreateEligibility.builder()
            .create(create)
            .supportedChannelIds(channelIds)
            .build();

    @Test
    void shouldSupportConfiguredChannelIds() {
        Channel channel = DefaultChannelMother.withId(SUPPORTED_CHANNEL_ID);
        CreateEligibilityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        boolean supported = channelCreate.supports(request);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherChannelIds() {
        Channel channel = DefaultChannelMother.withId("other-channel");
        CreateEligibilityRequest request = CreateEligibilityRequestMother.withChannel(channel);

        boolean supported = channelCreate.supports(request);

        assertThat(supported).isFalse();
    }

    @Test
    void shouldPerformCreate() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        IdentityEligibility expectedEligibility = IdentityEligibilityMother.build();
        given(create.create(request)).willReturn(expectedEligibility);

        IdentityEligibility eligibility = channelCreate.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

}
