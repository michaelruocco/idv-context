package uk.co.idv.lockout.entities.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;
import uk.co.idv.lockout.entities.policy.LockoutStateRequestMother;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;

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
