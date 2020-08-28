package uk.co.idv.context.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.eligibility.EligibilityMother;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ChannelCreateEligibilityTest {

    private final CreateEligibility create = mock(CreateEligibility.class);
    private final Collection<String> channelIds = Collections.singletonList("default-channel");

    private final ChannelCreateEligibility channelCreate = ChannelCreateEligibility.builder()
            .create(create)
            .supportedChannelIds(channelIds)
            .build();

    @Test
    void shouldReturnSupportedChannelIds() {
        assertThat(channelCreate.getSupportedChannelIds()).isEqualTo(channelIds);
    }

    @Test
    void shouldPerformCreate() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        Eligibility expectedEligibility = EligibilityMother.build();
        given(create.create(request)).willReturn(expectedEligibility);

        Eligibility eligibility = channelCreate.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }
}
