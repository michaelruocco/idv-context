package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextPolicyTest {

    @Test
    void shouldReturnRequestedDataFromSequencePolicies() {
        RequestedData expectedRequestedData = RequestedDataMother.allRequested();
        ContextPolicy policy = new ContextPolicy(givenSequencePoliciesWithRequestedData(expectedRequestedData));

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

    @Test
    void shouldReturnSequencePolicies() {
        SequencePolicies expectedSequencePolicies = givenSequencePolicies();
        ContextPolicy policy = new ContextPolicy(expectedSequencePolicies);

        SequencePolicies sequencePolicies = policy.getSequencePolicies();

        assertThat(sequencePolicies).isEqualTo(expectedSequencePolicies);
    }

    private SequencePolicies givenSequencePoliciesWithRequestedData(RequestedData data) {
        SequencePolicies policies = givenSequencePolicies();
        given(policies.getRequestedData()).willReturn(data);
        return policies;
    }

    private SequencePolicies givenSequencePolicies() {
        return mock(SequencePolicies.class);
    }

}
