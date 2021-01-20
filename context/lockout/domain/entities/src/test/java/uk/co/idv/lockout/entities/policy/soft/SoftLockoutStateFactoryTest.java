package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.AttemptMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockoutStateFactoryTest {

    private final Duration duration = Duration.ofMinutes(5);

    private final SoftLockoutStateFactory factory = new SoftLockoutStateFactory();

    @Test
    void shouldReturnUnlockedStateThereAreNoAttempts() {
        Attempts attempts = AttemptsMother.empty();
        Instant requestTime = Instant.now();

        LockoutState state = factory.build(duration, requestTime, attempts);

        assertThat(state).isInstanceOf(UnlockedState.class);
        assertThat(state.getAttempts()).isEmpty();
    }
    @Test
    void shouldReturnUnlockedStateIfNewAttemptIsAfterExpiry() {
        Instant expiry = Instant.now();
        Instant requestTime = expiry.plusMillis(1);
        Attempts attempts = givenAttemptsWithExpiry(expiry);

        LockoutState state = factory.build(duration, requestTime, attempts);

        assertThat(state).isInstanceOf(UnlockedState.class);
        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnSoftLockoutStateIfNewAttemptIsBeforeExpiry() {
        Instant expiry = Instant.now();
        Instant requestTime = expiry.minusMillis(1);
        Attempts attempts = givenAttemptsWithExpiry(expiry);

        SoftLockoutState state = (SoftLockoutState) factory.build(duration, requestTime, attempts);

        assertThat(state.getExpiry()).isEqualTo(expiry);
        assertThat(state.getDuration()).isEqualTo(duration);
        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    private Attempts givenAttemptsWithExpiry(Instant expiry) {
        return AttemptsMother.withAttempts(AttemptMother.withTimestamp(expiry.minus(duration)));
    }

}
