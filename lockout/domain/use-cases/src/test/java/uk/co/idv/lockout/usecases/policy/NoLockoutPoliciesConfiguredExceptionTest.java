package uk.co.idv.lockout.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.entities.policy.PolicyRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class NoLockoutPoliciesConfiguredExceptionTest {

    @Test
    void shouldReturnRequest() {
        PolicyRequest request = PolicyRequestMother.build();

        NoLockoutPoliciesConfiguredException exception = new NoLockoutPoliciesConfiguredException(request);

        assertThat(exception.getRequest()).isEqualTo(request);
    }

}
