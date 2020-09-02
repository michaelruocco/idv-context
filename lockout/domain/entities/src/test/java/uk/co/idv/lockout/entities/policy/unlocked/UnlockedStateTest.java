package uk.co.idv.lockout.entities.policy.unlocked;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.LockoutState;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UnlockedStateTest {

    private final Attempts attempts = AttemptsMother.build();
    private final LockoutState state = new UnlockedState(attempts);

    @Test
    void shouldReturnIdFromAttempts() {
        UUID id = state.getId();

        assertThat(id).isEqualTo(attempts.getId());
    }

    @Test
    void shouldReturnIdvIdFromAttempts() {
        IdvId idvId = state.getIdvId();

        assertThat(idvId).isEqualTo(attempts.getIdvId());
    }

    @Test
    void shouldReturnAttempts() {
        Attempts stateAttempts = state.getAttempts();

        assertThat(stateAttempts).isEqualTo(attempts);
    }

    @Test
    void shouldReturnNumberOfAttempts() {
        int numberOfAttempts = state.getNumberOfAttempts();

        assertThat(numberOfAttempts).isEqualTo(attempts.size());
    }

    @Test
    void shouldNotBeLocked() {
        boolean locked = state.isLocked();

        assertThat(locked).isFalse();
    }

    @Test
    void shouldReturnMessage() {
        String message = state.getMessage();

        assertThat(message).isEqualTo("unlocked");
    }

}
