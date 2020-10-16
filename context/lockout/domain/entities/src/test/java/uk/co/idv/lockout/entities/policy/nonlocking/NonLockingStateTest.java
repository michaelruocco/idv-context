package uk.co.idv.lockout.entities.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.LockoutState;

import static org.assertj.core.api.Assertions.assertThat;

class NonLockingStateTest {

    private final Attempts attempts = AttemptsMother.build();

    private final LockoutState state = new NonLockingState(attempts);

    @Test
    void shouldReturnMessage() {
        String message = state.getMessage();

        assertThat(message).isEqualTo("non locking policy");
    }

}
