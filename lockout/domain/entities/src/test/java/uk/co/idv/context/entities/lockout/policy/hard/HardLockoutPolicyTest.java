package uk.co.idv.context.entities.lockout.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.AlwaysRecordAttempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HardLockoutPolicyTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;
    private final PolicyKey key = mock(PolicyKey.class);

    private final LockoutPolicy policy = new HardLockoutPolicy(key, MAX_NUMBER_OF_ATTEMPTS);

    @Test
    void shouldReturnKey() {
        assertThat(policy.getKey()).isEqualTo(key);
    }

    @Test
    void shouldReturnStateCalculator() {
        assertThat(policy.getStateCalculator()).isInstanceOf(HardLockoutStateCalculator.class);
    }

    @Test
    void shouldReturnStateCalculatorWithMaxNumberOfAttempts() {
        HardLockoutStateCalculator calculator = (HardLockoutStateCalculator) policy.getStateCalculator();

        assertThat(calculator.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    @Test
    void shouldReturnRecordAttemptsPolicy() {
        assertThat(policy.getRecordAttemptPolicy()).isInstanceOf(AlwaysRecordAttempts.class);
    }

}
