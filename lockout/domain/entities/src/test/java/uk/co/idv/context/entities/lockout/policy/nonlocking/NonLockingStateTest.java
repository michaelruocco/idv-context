package uk.co.idv.context.entities.lockout.policy.nonlocking;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

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
