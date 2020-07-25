package uk.co.idv.context.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutStateTest {

    private final SoftLock lock = SoftLockMother.build();
    private final VerificationAttempts attempts = mock(VerificationAttempts.class);

    private final SoftLockoutState state = new SoftLockoutState(attempts, lock);

    @Test
    void shouldReturnDurationFromLock() {
        assertThat(state.getDuration()).isEqualTo(lock.getDuration());
    }

    @Test
    void shouldReturnExpiryFromLock() {
        assertThat(state.getExpiry()).isEqualTo(lock.getExpiry());
    }

    @Test
    void shouldReturnNumberOfAttempts() {
        int numberOfAttempts = 2;
        givenNumberOfAttempts(numberOfAttempts);

        assertThat(state.getNumberOfAttempts()).isEqualTo(numberOfAttempts);
    }

    @Test
    void shouldBeLocked() {
        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnMessage() {
        assertThat(state.getMessage()).isEqualTo(toExpectedLockedMessage());
    }

    private void givenNumberOfAttempts(int numberOfAttempts) {
        given(attempts.size()).willReturn(numberOfAttempts);
    }

    private String toExpectedLockedMessage() {
        return String.format("soft lock expiring at %s", lock.getExpiry());
    }

}
