package uk.co.idv.policy.usecases.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.policy.entities.policy.FakePolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.key.PolicyKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PolicyPopulatorTest {

    private final PolicyService<Policy> service = mock(PolicyService.class);

    private final PolicyPopulator<Policy> populator = new PolicyPopulator<>(service);

    @Test
    void shouldNotPopulatePolicyIfPolicyAlreadyExistsThatMatchesKey() {
        Policy policy = FakePolicyMother.build();
        givenPoliciesExistMatchingKey(policy.getKey());

        boolean populated = populator.tryPopulate(policy);

        assertThat(populated).isFalse();
        verify(service, never()).create(any(Policy.class));
    }

    @Test
    void shouldPopulatePolicyIfNoPoliciesExistThatMatchKey() {
        Policy policy = FakePolicyMother.build();
        givenNoPoliciesExistMatchingKey(policy.getKey());

        boolean populated = populator.tryPopulate(policy);

        assertThat(populated).isTrue();
        verify(service).create(policy);
    }

    private void givenPoliciesExistMatchingKey(PolicyKey key) {
        Policies<Policy> policies = givenPoliciesExist(key);
        given(policies.isEmpty()).willReturn(false);
    }

    private void givenNoPoliciesExistMatchingKey(PolicyKey key) {
        Policies<Policy> policies = givenPoliciesExist(key);
        given(policies.isEmpty()).willReturn(true);
    }

    private Policies<Policy> givenPoliciesExist(PolicyKey key) {
        Policies<Policy> policies = mock(Policies.class);
        given(service.load(key)).willReturn(policies);
        return policies;
    }

}
