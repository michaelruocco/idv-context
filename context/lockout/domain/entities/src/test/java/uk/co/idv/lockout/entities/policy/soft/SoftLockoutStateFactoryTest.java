package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.LockoutStateRequest;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutStateFactoryTest {

    private final LockoutStateRequest request = mock(LockoutStateRequest.class);
    private final Duration duration = Duration.ofMinutes(5);

    private final SoftLockoutStateFactory factory = new SoftLockoutStateFactory();

    @Test
    void shouldReturnUnlockedStateThereAreNoAttempts() {
        Attempts attempts = AttemptsMother.empty();
        given(request.getAttempts()).willReturn(attempts);
        given(request.getMostRecentAttemptTimestamp()).willReturn(Optional.empty());

        LockoutState state = factory.build(duration, request);

        assertThat(state).isInstanceOf(UnlockedState.class);
        assertThat(state.getAttempts()).isEmpty();
    }
    @Test
    void shouldReturnUnlockedStateIfNewAttemptIsAfterExpiry() {
        Attempts attempts = AttemptsMother.build();
        Instant expiry = Instant.now();
        givenExpiry(expiry);
        givenNewAttemptAfter(expiry);
        given(request.getAttempts()).willReturn(attempts);

        LockoutState state = factory.build(duration, request);

        assertThat(state).isInstanceOf(UnlockedState.class);
        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnSoftLockoutStateIfNewAttemptIsBeforeExpiry() {
        Attempts attempts = AttemptsMother.build();
        Instant expiry = Instant.now();
        givenExpiry(expiry);
        givenNewAttemptBefore(expiry);
        given(request.getAttempts()).willReturn(attempts);

        SoftLockoutState state = (SoftLockoutState) factory.build(duration, request);

        assertThat(state.getExpiry()).isEqualTo(expiry);
        assertThat(state.getDuration()).isEqualTo(duration);
        assertThat(state.getAttempts()).isEqualTo(attempts);
    }

    private void givenExpiry(Instant expiry) {
        given(request.getMostRecentAttemptTimestamp()).willReturn(Optional.of(expiry.minus(duration)));
    }

    private void givenNewAttemptAfter(Instant expiry) {
        given(request.isBefore(expiry)).willReturn(false);
    }

    private void givenNewAttemptBefore(Instant expiry) {
        given(request.isBefore(expiry)).willReturn(true);
    }

}
