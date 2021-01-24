package uk.co.idv.context.entities.policy.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.policy.MethodPolicies;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class StagePolicyTest {

    private final MethodPolicies methodPolicies = mock(MethodPolicies.class);
    private final StageType type = mock(StageType.class);

    private final StagePolicy policy = StagePolicy.builder()
            .methodPolicies(methodPolicies)
            .type(type)
            .build();

    @Test
    void shouldReturnTypeName() {
        String expectedTypeName = "type-name";
        given(type.getName()).willReturn(expectedTypeName);

        String typeName = policy.getTypeName();

        assertThat(typeName).isEqualTo(expectedTypeName);
    }

    @Test
    void shouldReturnMethodPolicies() {
        assertThat(policy.getMethodPolicies()).isEqualTo(methodPolicies);
    }

    @Test
    void shouldReturnRequestedDataFromMethodPolicies() {
        RequestedData expectedRequestedData = mock(RequestedData.class);
        given(methodPolicies.getRequestedData()).willReturn(expectedRequestedData);

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

    @Test
    void shouldReturnCompletionPolicy() {
        assertThat(policy.getType()).isEqualTo(type);
    }

}
