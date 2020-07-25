package uk.co.idv.context.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.policy.LockoutState;
import uk.co.idv.context.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.lockout.policy.LockoutStateRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class NonLockingStateCalculatorTest {

    private final LockoutStateCalculator calculator = new NonLockingStateCalculator();

    @Test
    void shouldReturnType() {
        assertThat(calculator.getType()).isEqualTo("non-locking");
    }

    @Test
    void shouldReturnNonLockingState() {
        LockoutStateRequest request = LockoutStateRequestMother.build();

        LockoutState state = calculator.calculate(request);

        assertThat(state).isInstanceOf(NonLockingState.class);
    }

    @Test
    void shouldPopulateAttemptsOnLockoutState() {
        LockoutStateRequest request = LockoutStateRequestMother.build();

        LockoutState state = calculator.calculate(request);

        assertThat(state.getAttempts()).isEqualTo(request.getAttempts());
    }

}
