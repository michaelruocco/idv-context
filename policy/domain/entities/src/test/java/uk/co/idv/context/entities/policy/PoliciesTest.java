package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PoliciesTest {

    @Test
    void shouldReturnApplicablePolicies() {
        PolicyRequest request = mock(PolicyRequest.class);
        Policy applicable = givenPolicyApplicableTo(request);
        Policy notApplicable = mock(Policy.class);
        Policies<Policy> allPolicies = new Policies<>(applicable, notApplicable);

        Policies<Policy> policies = allPolicies.getApplicable(request);

        assertThat(policies).containsExactly(applicable);
    }

    @Test
    void shouldThrowExceptionIfHighestPriorityRequestedWhenEmpty() {
        Policies<Policy> emptyPolicies = new Policies<>();

        Throwable error = catchThrowable(emptyPolicies::getHighestPriority);

        assertThat(error).isInstanceOf(EmptyPoliciesException.class);
    }

    @Test
    void shouldReturnPolicyWithHighestPriorityValue() {
        Policy lowPriority = givenPolicyWithPriority(1);
        Policy highPriority = givenPolicyWithPriority(99);
        Policies<Policy> policies = new Policies<>(lowPriority, highPriority);

        Policy policy = policies.getHighestPriority();

        assertThat(policy).isEqualTo(highPriority);
    }

    @Test
    void shouldReturnPolicyWithHighestPriorityValueAddedFirstIfTwoPoliciesHaveSamePriority() {
        Policy lowPriority = givenPolicyWithPriority(1);
        Policy highPriority = givenPolicyWithPriority(99);
        Policy otherHighPriority = givenPolicyWithPriority(99);
        Policies<Policy> policies = new Policies<>(lowPriority, highPriority, otherHighPriority);

        Policy policy = policies.getHighestPriority();

        assertThat(policy).isEqualTo(highPriority);
    }

    @Test
    void shouldReturnStreamOfPolicies() {
        Policy policy1 = mock(Policy.class);
        Policy policy2 = mock(Policy.class);

        Policies<Policy> policies = new Policies<>(policy1, policy2);

        assertThat(policies.stream()).containsExactly(policy1, policy2);
    }

    private Policy givenPolicyApplicableTo(PolicyRequest request) {
        Policy policy = mock(Policy.class);
        given(policy.appliesTo(request)).willReturn(true);
        return policy;
    }

    private Policy givenPolicyWithPriority(int priority) {
        Policy policy = mock(Policy.class);
        given(policy.getPriority()).willReturn(priority);
        return policy;
    }

}
