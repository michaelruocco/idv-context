package uk.co.idv.context.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.PolicyRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class NoContextPoliciesConfiguredHandlerTest {

    private final NoContextPoliciesConfiguredHandler handler = new NoContextPoliciesConfiguredHandler();

    @Test
    void shouldConvertRequestToException() {
        PolicyRequest request = PolicyRequestMother.build();

        NoContextPoliciesConfiguredException exception = handler.toException(request);

        assertThat(exception.getRequest()).isEqualTo(request);
    }

}
