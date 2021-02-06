package uk.co.idv.lockout.usecases.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.LockedOutException;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.state.MockLockoutStateMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ValidateLockoutStateTest {

    private final ValidateLockoutState validate = new ValidateLockoutState();

    @Test
    void shouldNotThrowExceptionIfLockoutStateIsUnlocked() {
        LockoutState state = MockLockoutStateMother.unlocked();

        assertThatCode(() -> validate.validate(state)).doesNotThrowAnyException();
    }

    @Test
    void shouldThrowLockedOutExceptionIfLockoutStateIsLocked() {
        LockoutState state = MockLockoutStateMother.locked();

        LockedOutException error = catchThrowableOfType(
                () -> validate.validate(state),
                LockedOutException.class
        );

        assertThat(error.getState()).isEqualTo(state);
    }

}
