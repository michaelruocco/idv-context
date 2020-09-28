package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SequencesRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID id = UUID.randomUUID();

        MethodsRequest request = MethodsRequest.builder()
                .contextId(id)
                .build();

        assertThat(request.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = mock(Identity.class);

        MethodsRequest request = MethodsRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnMethodPolicies() {
        MethodPolicies policies = mock(MethodPolicies.class);

        MethodsRequest request = MethodsRequest.builder()
                .policies(policies)
                .build();

        assertThat(request.getPolicies()).isEqualTo(policies);
    }

}
