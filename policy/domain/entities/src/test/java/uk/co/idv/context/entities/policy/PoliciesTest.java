package uk.co.idv.context.entities.policy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

class PoliciesTest {

    @Test
    void shouldReturnApplicablePolicies() {
        PolicyRequest request = mock(DefaultPolicyRequest.class);
        Policy applicable = MockPolicyMother.applicableTo(request);
        Policy notApplicable = MockPolicyMother.notApplicableTo(request);
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
        Policy lowPriority = MockPolicyMother.withPriority(1);
        Policy highPriority = MockPolicyMother.withPriority(99);
        Policies<Policy> policies = new Policies<>(lowPriority, highPriority);

        Policy policy = policies.getHighestPriority();

        assertThat(policy).isEqualTo(highPriority);
    }

    @Test
    void shouldReturnPolicyWithHighestPriorityValueAddedFirstIfTwoPoliciesHaveSamePriority() {
        Policy lowPriority = MockPolicyMother.withPriority(1);
        Policy highPriority = MockPolicyMother.withPriority(99);
        Policy otherHighPriority = MockPolicyMother.withPriority(99);
        Policies<Policy> policies = new Policies<>(lowPriority, highPriority, otherHighPriority);

        Policy policy = policies.getHighestPriority();

        assertThat(policy).isEqualTo(highPriority);
    }

    @Test
    void shouldReturnStreamOfPolicies() {
        Policy policy1 = MockPolicyMother.policy();
        Policy policy2 = MockPolicyMother.policy();

        Policies<Policy> policies = new Policies<>(policy1, policy2);

        assertThat(policies.stream()).containsExactly(policy1, policy2);
    }

    @Test
    void shouldReturnIsEmptyTrueIfEmpty() {
        Policies<Policy> emptyPolicies = new Policies<>();

        assertThat(emptyPolicies.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnIsEmptyFalseIfNotEmpty() {
        Policy policy = MockPolicyMother.policy();

        Policies<Policy> policies = new Policies<>(policy);

        assertThat(policies.isEmpty()).isFalse();
    }

}
