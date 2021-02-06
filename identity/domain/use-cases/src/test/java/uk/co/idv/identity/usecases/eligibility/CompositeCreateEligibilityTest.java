package uk.co.idv.identity.usecases.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequestMother;
import uk.co.idv.identity.entities.eligibility.EligibilityNotConfiguredException;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.eligibility.IdentityEligibilityMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeCreateEligibilityTest {

    private final ChannelCreateEligibility channelCreate1 = mock(ChannelCreateEligibility.class);
    private final ChannelCreateEligibility channelCreate2 = mock(ChannelCreateEligibility.class);

    private final CompositeCreateEligibility compositeCreate = new CompositeCreateEligibility(
            channelCreate1,
            channelCreate2
    );

    @Test
    void shouldPerformCreateForGivenChannelId() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        given(channelCreate1.supports(request)).willReturn(false);
        given(channelCreate2.supports(request)).willReturn(true);
        IdentityEligibility expectedEligibility = IdentityEligibilityMother.build();
        given(channelCreate2.create(request)).willReturn(expectedEligibility);

        IdentityEligibility eligibility = compositeCreate.create(request);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    @Test
    void shouldThrowExceptionIfCreateNotConfiguredForChannelId() {
        CreateEligibilityRequest request = CreateEligibilityRequestMother.build();
        given(channelCreate1.supports(request)).willReturn(false);
        given(channelCreate2.supports(request)).willReturn(false);

        Throwable error = catchThrowable(() -> compositeCreate.create(request));

        assertThat(error)
                .isInstanceOf(EligibilityNotConfiguredException.class)
                .hasMessage(request.getChannelId());
    }

}
