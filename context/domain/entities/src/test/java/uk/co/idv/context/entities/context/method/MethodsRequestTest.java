package uk.co.idv.context.entities.context.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.entities.policy.sequence.SequencePolicyMother;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MethodsRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID id = UUID.randomUUID();

        SequencesRequest request = SequencesRequest.builder()
                .contextId(id)
                .build();

        assertThat(request.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = mock(Identity.class);

        SequencesRequest request = SequencesRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnSequencePolicies() {
        SequencePolicies policies = mock(SequencePolicies.class);

        SequencesRequest request = SequencesRequest.builder()
                .policies(policies)
                .build();

        assertThat(request.getPolicies()).isEqualTo(policies);
    }

    @Test
    void shouldPopulateContextIdOnMethodsRequest() {
        UUID id = UUID.randomUUID();
        SequencesRequest sequencesRequest = SequencesRequest.builder()
                .contextId(id)
                .build();

        MethodsRequest methodsRequest = sequencesRequest.toMethodsRequest(SequencePolicyMother.build());

        assertThat(methodsRequest.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldPopulateIdentityOnMethodsRequest() {
        Identity identity = mock(Identity.class);
        SequencesRequest sequencesRequest = SequencesRequest.builder()
                .identity(identity)
                .build();

        MethodsRequest methodsRequest = sequencesRequest.toMethodsRequest(SequencePolicyMother.build());

        assertThat(methodsRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPopulateMethodPoliciesOnMethodsRequest() {
        Identity identity = mock(Identity.class);
        SequencesRequest sequencesRequest = SequencesRequest.builder()
                .identity(identity)
                .build();
        SequencePolicy policy = SequencePolicyMother.build();

        MethodsRequest methodsRequest = sequencesRequest.toMethodsRequest(policy);

        assertThat(methodsRequest.getPolicies()).isEqualTo(policy.getMethodPolicies());
    }

}
