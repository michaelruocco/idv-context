package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateRequestMother;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockoutStateCalculatorTest {

    private final SoftLockIntervals intervals = SoftLockIntervalsMother.oneAttempt();

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
