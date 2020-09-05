package uk.co.idv.context.entities.policy.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencePolicyTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        SequencePolicy policy = SequencePolicy.builder()
                .name(name)
                .build();

        assertThat(policy.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnMethodPolicies() {
        MethodPolicies methodPolicies = givenMethodPolicies();

        SequencePolicy policy = SequencePolicy.builder()
                .methodPolicies(methodPolicies)
                .build();

        assertThat(policy.getMethodPolicies()).isEqualTo(methodPolicies);
    }

    @Test
    void shouldReturnRequestedDataFromMethodPolicies() {
        RequestedData requestedData = RequestedDataMother.allRequested();

        SequencePolicy policy = SequencePolicy.builder()
                .methodPolicies(givenMethodPoliciesWithRequestedData(requestedData))
                .build();

        assertThat(policy.getRequestedData()).isEqualTo(requestedData);
    }

    private MethodPolicies givenMethodPoliciesWithRequestedData(RequestedData requestedData) {
        MethodPolicies methodPolicies = givenMethodPolicies();
        given(methodPolicies.getRequestedData()).willReturn(requestedData);
        return methodPolicies;
    }

    private MethodPolicies givenMethodPolicies() {
        return mock(MethodPolicies.class);
    }


}
