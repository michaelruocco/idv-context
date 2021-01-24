package uk.co.idv.context.entities.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicyMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.method.MethodsRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StagesRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID id = UUID.randomUUID();

        StagesRequest request = StagesRequest.builder()
                .contextId(id)
                .build();

        assertThat(request.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldReturnIdentity() {
        Identity identity = mock(Identity.class);

        StagesRequest request = StagesRequest.builder()
                .identity(identity)
                .build();

        assertThat(request.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldReturnStagePolicies() {
        StagePolicies policies = mock(StagePolicies.class);

        StagesRequest request = StagesRequest.builder()
                .policies(policies)
                .build();

        assertThat(request.getPolicies()).isEqualTo(policies);
    }

    @Test
    void shouldPopulateContextIdOnMethodsRequest() {
        UUID id = UUID.randomUUID();
        StagesRequest sequencesRequest = StagesRequest.builder()
                .contextId(id)
                .build();

        MethodsRequest methodsRequest = sequencesRequest.toMethodsRequest(StagePolicyMother.build());

        assertThat(methodsRequest.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldPopulateIdentityOnMethodsRequest() {
        Identity identity = mock(Identity.class);
        StagesRequest sequencesRequest = StagesRequest.builder()
                .identity(identity)
                .build();

        MethodsRequest methodsRequest = sequencesRequest.toMethodsRequest(StagePolicyMother.build());

        assertThat(methodsRequest.getIdentity()).isEqualTo(identity);
    }

    @Test
    void shouldPopulateStagePoliciesOnMethodsRequest() {
        Identity identity = mock(Identity.class);
        StagesRequest sequencesRequest = StagesRequest.builder()
                .identity(identity)
                .build();

        MethodsRequest methodsRequest = sequencesRequest.toMethodsRequest(StagePolicyMother.build());

        assertThat(methodsRequest.getIdentity()).isEqualTo(identity);
    }

}
