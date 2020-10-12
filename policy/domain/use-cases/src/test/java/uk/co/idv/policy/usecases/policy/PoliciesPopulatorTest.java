package uk.co.idv.policy.usecases.policy;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.policy.entities.policy.MockPolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.PoliciesMother;
import uk.co.idv.policy.entities.policy.Policy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PoliciesPopulatorTest {

    private final PolicyPopulator<Policy> policyPopulator = mock(PolicyPopulator.class);

    private final PoliciesPopulator<Policy> policiesPopulator = new PoliciesPopulator<>(policyPopulator);

    @Test
    void shouldTryToPopulateAllPolicies() {
        Policy policy1 = MockPolicyMother.policy();
        Policy policy2 = MockPolicyMother.policy();
        Policies<Policy> policies = PoliciesMother.with(policy1, policy2);

        policiesPopulator.populate(policies);

        ArgumentCaptor<Policy> captor = ArgumentCaptor.forClass(Policy.class);
        verify(policyPopulator, times(2)).tryPopulate(captor.capture());
        assertThat(captor.getAllValues()).containsExactly(policy1, policy2);
    }

}
