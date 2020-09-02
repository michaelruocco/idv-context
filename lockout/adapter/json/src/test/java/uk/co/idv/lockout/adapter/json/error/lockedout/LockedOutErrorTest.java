package uk.co.idv.lockout.adapter.json.error.lockedout;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateMother;

import static org.assertj.core.api.Assertions.assertThat;

class LockedOutErrorTest {

    private final LockoutState state = HardLockoutStateMother.build();

    private final ApiError error = new LockedOutError(state);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(423);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Identity locked");
    }

    @Test
    void shouldReturnMessage() {
        String expected = String.format("Identity with %s locked", state.getIdvId().format());

        String message = error.getMessage();

        assertThat(message).isEqualTo(expected);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).containsEntry("state", state);
    }

}
