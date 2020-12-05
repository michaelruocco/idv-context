package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextPolicyTest {

    private final PolicyKey key = mock(PolicyKey.class);
    private final SequencePolicies sequencePolicies = mock(SequencePolicies.class);
    private final boolean maskSensitiveData = true;

    private final ContextPolicy policy = ContextPolicy.builder()
            .key(key)
            .sequencePolicies(sequencePolicies)
            .maskSensitiveData(maskSensitiveData)
            .build();

    @Test
    void shouldReturnKey() {
        assertThat(policy.getKey()).isEqualTo(key);
    }

    @Test
    void shouldReturnIdFromKey() {
        UUID expectedId = UUID.randomUUID();
        given(key.getId()).willReturn(expectedId);

        UUID id = policy.getId();

        assertThat(id).isEqualTo(expectedId);
    }

    @Test
    void shouldReturnPriorityFromKey() {
        int expectedPriority = 99;
        given(key.getPriority()).willReturn(expectedPriority);

        int priority = policy.getPriority();

        assertThat(priority).isEqualTo(expectedPriority);
    }

    @Test
    void shouldReturnAppliesToFromKey() {
        PolicyRequest request = mock(PolicyRequest.class);
        given(key.appliesTo(request)).willReturn(true);

        boolean applies = policy.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldReturnRequestedDataFromSequencePolicies() {
        RequestedData expectedRequestedData = RequestedDataMother.allRequested();
        given(sequencePolicies.getRequestedData()).willReturn(expectedRequestedData);

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

    @Test
    void shouldReturnSequencePolicies() {
        assertThat(policy.getSequencePolicies()).isEqualTo(sequencePolicies);
    }

    @Test
    void shouldReturnMaskSensitiveData() {
        assertThat(policy.isMaskSensitiveData()).isEqualTo(maskSensitiveData);
    }

}
