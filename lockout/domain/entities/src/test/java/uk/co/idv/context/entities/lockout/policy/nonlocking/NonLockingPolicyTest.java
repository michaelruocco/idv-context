package uk.co.idv.context.entities.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class NonLockingPolicyTest {

    private final PolicyKey key = mock(PolicyKey.class);

    private final LockoutPolicy policy = new NonLockingPolicy(key);

    @Test
    void shouldReturnKey() {
        assertThat(policy.getKey()).isEqualTo(key);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isInstanceOf(NonLockingStateCalculator.class);
    }

    @Test
    void shouldReturnRecordAttemptsPolicy() {
        assertThat(policy.getRecordAttemptPolicy()).isInstanceOf(NeverRecordAttempts.class);
    }

}
