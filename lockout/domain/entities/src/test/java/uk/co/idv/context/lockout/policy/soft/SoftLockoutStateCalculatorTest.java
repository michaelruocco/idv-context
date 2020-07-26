package uk.co.idv.context.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.policy.LockoutState;
import uk.co.idv.context.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.lockout.policy.LockoutStateRequestMother;
import uk.co.idv.context.lockout.policy.unlocked.UnlockedState;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockoutStateCalculatorTest {

    private final SoftLockIntervals intervals = SoftLockIntervalsMother.onlyOneAttempt();

    private final SoftLockoutStateCalculator calculator = new SoftLockoutStateCalculator(intervals);

    @Test
    void shouldReturnMaxIntervals() {
        assertThat(calculator.getIntervals()).isEqualTo(intervals);
    }

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo("soft-lockout");
    }

    @Test
    void shouldReturnUnlockedStateIfNoIntervalMatchingNumberOfAttempts() {
        LockoutStateRequest request = LockoutStateRequestMother.build();

        LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(UnlockedState.class);
        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

    @Test
    void shouldReturnSoftLockoutStateWithDurationFromIntervalIfNumberOfAttemptsMatchesInterval() {
        LockoutStateRequest request = LockoutStateRequestMother.withOneAttempt();

        SoftLockoutState state = (SoftLockoutState) calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

}
