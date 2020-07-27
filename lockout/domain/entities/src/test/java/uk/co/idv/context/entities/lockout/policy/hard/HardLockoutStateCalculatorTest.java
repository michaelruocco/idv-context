package uk.co.idv.context.entities.lockout.policy.hard;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.entities.lockout.policy.LockoutStateRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class HardLockoutStateCalculatorTest {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    private final HardLockoutStateCalculator calculator = new HardLockoutStateCalculator(MAX_NUMBER_OF_ATTEMPTS);

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        assertThat(calculator.getMaxNumberOfAttempts()).isEqualTo(MAX_NUMBER_OF_ATTEMPTS);
    }

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo("hard-lockout");
    }

    @Test
    void shouldReturnHardLockoutState() {
        LockoutStateRequest request = LockoutStateRequestMother.build();

        LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(HardLockoutState.class);
    }

    @Test
    void shouldPopulateAttemptsOnLockoutState() {
        LockoutStateRequest request = LockoutStateRequestMother.build();

        LockoutState state = calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

}
