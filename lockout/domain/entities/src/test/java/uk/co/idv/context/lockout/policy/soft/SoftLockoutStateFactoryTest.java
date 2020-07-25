package uk.co.idv.context.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.context.lockout.policy.LockoutState;
import uk.co.idv.context.lockout.policy.LockoutStateRequest;
import uk.co.idv.context.lockout.policy.unlocked.UnlockedState;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SoftLockoutStateFactoryTest {

    private final LockoutStateRequest request = mock(LockoutStateRequest.class);
    private final Duration duration = Duration.ofMinutes(5);
    private final VerificationAttempts attempts = VerificationAttemptsMother.build();

    private final SoftLockoutStateFactory factory = new SoftLockoutStateFactory();

    @Test
    void shouldReturnUnlockedStateIfNewAttemptIsAfterExpiry() {
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
        given(request.addToMostRecentAttemptTimestamp(duration)).willReturn(expiry);
    }

    private void givenNewAttemptAfter(Instant expiry) {
        given(request.isNewAttemptBefore(expiry)).willReturn(false);
    }

    private void givenNewAttemptBefore(Instant expiry) {
        given(request.isNewAttemptBefore(expiry)).willReturn(true);
    }

}
