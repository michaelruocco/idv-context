package uk.co.idv.context.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.policy.PolicyRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class NoPoliciesConfiguredForRequestExceptionTest {

    @Test
    void shouldReturnMessage() {
        PolicyRequest request = PolicyRequestMother.build();

        Throwable error = new NoPoliciesConfiguredForRequestException(request);

        assertThat(error.getMessage()).isEqualTo(
                "channel: default-channel, " +
                "activity: default-activity, " +
                "alias type: default-alias"
        );
    }

    @Test
    void shouldReturnRequest() {
        PolicyRequest request = PolicyRequestMother.build();

        NoPoliciesConfiguredForRequestException error = new NoPoliciesConfiguredForRequestException(request);

        assertThat(error.getRequest()).isEqualTo(request);
    }

}
