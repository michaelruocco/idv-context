package uk.co.idv.method.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MethodPoliciesTest {

    @Test
    void shouldReturnRequestedDataFromAllMethods() {
        String data1 = "data-1";
        String data2 = "data-2";
        MethodPolicies policies = new MethodPolicies(
                givenMethodPolicyWithRequestedData(data1),
                givenMethodPolicyWithRequestedData(data2)
        );

        RequestedData allData = policies.getRequestedData();

        assertThat(allData).containsExactly(
                data1,
                data2
        );
    }

    @Test
    void shouldReturnStreamOfSequencePolicies() {
        MethodPolicy policy1 = givenMethodPolicy();
        MethodPolicy policy2 = givenMethodPolicy();
        MethodPolicies policies = new MethodPolicies(policy1, policy2);

        Stream<MethodPolicy> stream = policies.stream();

        assertThat(stream).containsExactly(policy1, policy2);
    }

    @Test
    void shouldBeIterable() {
        MethodPolicy policy1 = givenMethodPolicy();
        MethodPolicy policy2 = givenMethodPolicy();

        MethodPolicies policies = new MethodPolicies(policy1, policy2);

        assertThat(policies).containsExactly(policy1, policy2);
    }

    private MethodPolicy givenMethodPolicyWithRequestedData(String... items) {
        MethodPolicy policy = givenMethodPolicy();
        given(policy.getRequestedData()).willReturn(RequestedDataMother.with(items));
        return policy;
    }

    private MethodPolicy givenMethodPolicy() {
        return mock(MethodPolicy.class);
    }

}
