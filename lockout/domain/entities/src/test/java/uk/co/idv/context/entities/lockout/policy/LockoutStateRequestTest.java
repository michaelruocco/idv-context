package uk.co.idv.context.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.AttemptMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateRequestTest {

    @Test
    void shouldReturnNewAttempt() {
        Attempt attempt = AttemptMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();

        assertThat(request.getNewAttempt()).isEqualTo(attempt);
    }

    @Test
    void shouldReturnAttempts() {
        Attempts attempts = AttemptsMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getAttempts()).isEqualTo(attempts);
    }

    @Test
    void shouldReturnNumberOfAttempts() {
        Attempts attempts = AttemptsMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();

        assertThat(request.getNumberOfAttempts()).isEqualTo(attempts.size());
    }

    @Test
    void shouldReturnAliasFromNewAttempt() {
        Attempt attempt = AttemptMother.build();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();

        assertThat(request.getAlias()).isEqualTo(attempt.getAlias());
    }

    @Test
    void shouldReturnTrueIfNewAttemptIsBeforeNow() {
        Attempt attempt = AttemptMother.build();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .newAttempt(attempt)
                .build();
        Instant now = attempt.getTimestamp().plusMillis(1);

        boolean isBefore = request.isNewAttemptBefore(now);

        assertThat(isBefore).isTrue();
    }

    @Test
    void shouldReturnFalseIfNewAttemptIsAfterNow() {
        Attempt attempt = AttemptMother.build();
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

    private Attempts verificationAttemptsWithMostRecentTimestamp(Instant timestamp) {
        Attempts attempts = mock(Attempts.class);
        given(attempts.getMostRecentTimestamp()).willReturn(timestamp);
        return attempts;
    }

}
