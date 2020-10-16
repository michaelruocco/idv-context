package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SoftLockoutStateTest {

    private final SoftLock lock = SoftLockMother.build();
    private final Attempts attempts = mock(Attempts.class);

    private final SoftLockoutState state = SoftLockoutState.builder()
            .attempts(attempts)
            .lock(lock)
            .build();

    @Test
    void shouldReturnDurationFromLock() {
        assertThat(state.getDuration()).isEqualTo(lock.getDuration());
    }

    @Test
    void shouldReturnExpiryFromLock() {
        assertThat(state.getExpiry()).isEqualTo(lock.calculateExpiry());
    }

    @Test
    void shouldBeLocked() {
        assertThat(state.isLocked()).isTrue();
    }

    @Test
    void shouldReturnMessage() {
        assertThat(state.getMessage()).isEqualTo(toExpectedLockedMessage());
    }

    private String toExpectedLockedMessage() {
        return String.format("soft lock began at %s and expiring at %s", lock.getStart(), lock.calculateExpiry());
    }

}
