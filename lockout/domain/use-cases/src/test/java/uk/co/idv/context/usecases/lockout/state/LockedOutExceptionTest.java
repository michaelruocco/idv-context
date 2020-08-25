package uk.co.idv.context.usecases.lockout.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.state.MockLockoutStateMother;

import static org.assertj.core.api.Assertions.assertThat;

class LockedOutExceptionTest {

    @Test
    void shouldReturnMessage() {
        LockoutState state = MockLockoutStateMother.locked();

        Throwable error = new LockedOutException(state);

        assertThat(error.getMessage()).isEqualTo(state.getIdvId().format());
    }

    @Test
    void shouldReturnState() {
        LockoutState state = MockLockoutStateMother.locked();

        LockedOutException error = new LockedOutException(state);

        assertThat(error.getState()).isEqualTo(state);
    }

}
