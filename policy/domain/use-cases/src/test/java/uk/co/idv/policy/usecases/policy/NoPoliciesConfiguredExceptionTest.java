package uk.co.idv.policy.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class NoPoliciesConfiguredExceptionTest {

    @Test
    void shouldReturnMessage() {
        PolicyRequest request = PolicyRequestMother.build();

        Throwable error = new NoPoliciesConfiguredException(request);

        assertThat(error.getMessage()).isEqualTo(
                "channel: default-channel, " +
                "activity: default-activity, " +
                "alias types: [default-alias]"
        );
    }

    @Test
    void shouldReturnRequest() {
        PolicyRequest request = PolicyRequestMother.build();

        NoPoliciesConfiguredException error = new NoPoliciesConfiguredException(request);

        assertThat(error.getRequest()).isEqualTo(request);
    }

}
