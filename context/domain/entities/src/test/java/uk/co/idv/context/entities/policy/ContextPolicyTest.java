package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.RequestedData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextPolicyTest {

    private final MethodPolicies methodPolicies = mock(MethodPolicies.class);

    private final ContextPolicy policy = new ContextPolicy(methodPolicies);

    @Test
    void shouldReturnRequestedDataFromMethodPolicies() {
        RequestedData expectedRequestedData = givenRequestedDataFromMethodPolicies();

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

    private RequestedData givenRequestedDataFromMethodPolicies() {
        RequestedData data = mock(RequestedData.class);
        given(methodPolicies.getRequestedData()).willReturn(data);
        return data;
    }

}
