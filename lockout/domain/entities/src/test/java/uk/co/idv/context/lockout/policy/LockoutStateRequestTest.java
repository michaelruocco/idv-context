package uk.co.idv.context.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.lockout.attempt.VerificationAttemptMother;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.lockout.attempt.VerificationAttemptsMother;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateRequestTest {

    @Test
    void shouldReturnNewAttempt() {
        VerificationAttempt attempt = VerificationAttemptMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();

        assertThat(request.getNewAttempt()).isEqualTo(attempt);
    }

    @Test
    void shouldReturnAttempts() {
        VerificationAttempts attempts = VerificationAttemptsMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnAliasFromNewAttempt() {
        VerificationAttempt attempt = VerificationAttemptMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();

        assertThat(request.getAlias()).isEqualTo(attempt.getAlias());
    }

    @Test
    void shouldReturnTrueIfNewAttemptIsBeforeNow() {
        VerificationAttempt attempt = VerificationAttemptMother.build();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();
        Instant now = attempt.getTimestamp().plusMillis(1);

        boolean isBefore = request.isNewAttemptBefore(now);

        assertThat(isBefore).isTrue();
    }

    @Test
    void shouldReturnFalseIfNewAttemptIsAfterNow() {
        VerificationAttempt attempt = VerificationAttemptMother.build();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();
        Instant now = attempt.getTimestamp().minusMillis(1);

        boolean isBefore = request.isNewAttemptBefore(now);

        assertThat(isBefore).isFalse();
    }

    @Test
    void shouldAddDurationToMostRecentTimestampFromAttempts() {
        Instant mostRecentTimestamp = Instant.now();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(verificationAttemptsWithMostRecentTimestamp(mostRecentTimestamp))
                .build();
        Duration duration = Duration.ofMinutes(5);

        Instant result = request.addToMostRecentAttemptTimestamp(duration);

        assertThat(result).isEqualTo(mostRecentTimestamp.plus(duration));
    }

    private VerificationAttempts verificationAttemptsWithMostRecentTimestamp(Instant timestamp) {
        VerificationAttempts attempts = mock(VerificationAttempts.class);
        given(attempts.getMostRecentTimestamp()).willReturn(timestamp);
        return attempts;
    }

}
