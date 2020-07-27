package uk.co.idv.context.entities.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

import static org.assertj.core.api.Assertions.assertThat;

class NonLockingStateTest {

    private final VerificationAttempts attempts = VerificationAttemptsMother.build();

    private final LockoutState state = new NonLockingState(attempts);

    @Test
    void shouldReturnMessage() {
        String message = state.getMessage();

        assertThat(message).isEqualTo("non locking policy");
    }

}
