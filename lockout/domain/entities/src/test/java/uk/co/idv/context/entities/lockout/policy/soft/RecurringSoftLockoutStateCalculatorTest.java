package uk.co.idv.context.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequestMother;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

import static org.assertj.core.api.Assertions.assertThat;

class RecurringSoftLockoutStateCalculatorTest {

    private static final int INTERVAL_NUMBER_OF_ATTEMPTS = 2;

    private final SoftLockInterval interval = SoftLockIntervalMother.build(INTERVAL_NUMBER_OF_ATTEMPTS);

    private final RecurringSoftLockoutStateCalculator calculator = new RecurringSoftLockoutStateCalculator(interval);

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo("recurring-soft-lockout");
    }

    @Test
    void shouldReturnInterval() {
        assertThat(calculator.getInterval()).isEqualTo(interval);
    }

    @Test
    void shouldReturnNotLockedStateIfNumberOfAttemptsIsNotDivisibleByIntervalNumberOfAttempts() {
        LockoutStateRequest request = LockoutStateRequestMother.withOneAttempt();

        LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(UnlockedState.class);
        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

    @Test
    void shouldReturnSoftLockoutStateIfNumberOfAttemptsIsDivisibleByIntervalNumberOfAttempts() {
        LockoutStateRequest request = LockoutStateRequestMother.withNumberOfAttempts(INTERVAL_NUMBER_OF_ATTEMPTS);

        SoftLockoutState state = (SoftLockoutState) calculator.calculate(request);

        assertThat(state.getDuration()).isEqualTo(interval.getDuration());
    }

}
