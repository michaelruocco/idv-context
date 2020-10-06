package uk.co.idv.method.entities.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.policy.MethodPolicies;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MethodRequestTest {

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
