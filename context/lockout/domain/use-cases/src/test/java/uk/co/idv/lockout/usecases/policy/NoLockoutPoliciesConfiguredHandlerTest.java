package uk.co.idv.lockout.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class NoLockoutPoliciesConfiguredHandlerTest {

    private final NoLockoutPoliciesConfiguredHandler handler = new NoLockoutPoliciesConfiguredHandler();

    @Test
    void shouldConvertRequestToException() {
        PolicyRequest request = PolicyRequestMother.build();

        NoLockoutPoliciesConfiguredException exception = handler.toException(request);

        assertThat(exception.getRequest()).isEqualTo(request);
    }

}
