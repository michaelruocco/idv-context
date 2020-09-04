package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextPolicyTest {

    private final SequencePolicies sequencePolicies = mock(SequencePolicies.class);

    private final ContextPolicy policy = new ContextPolicy(sequencePolicies);

    @Test
    void shouldReturnRequestedDataFromSequencePolicies() {
        RequestedData expectedRequestedData = givenRequestedDataFromSequencePolicies();

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

    private RequestedData givenRequestedDataFromSequencePolicies() {
        RequestedData data = mock(RequestedData.class);
        given(sequencePolicies.getRequestedData()).willReturn(data);
        return data;
    }

}
