package uk.co.idv.lockout.entities.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LockoutStateRequestTest {

    @Test
    void shouldReturnAliases() {
        DefaultAliases aliases = AliasesMother.defaultAliasOnly();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .aliases(aliases)
                .build();

        assertThat(request.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        LockoutStateRequest request = LockoutStateRequest.builder()
                .timestamp(timestamp)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
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
    void shouldReturnRequestWithUpdatedAttemptsAndAllOtherValuesUnchanged() {
        Attempts attempts = AttemptsMother.withAttempts(AttemptMother.build());
        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(attempts)
                .build();
        Attempts empty = AttemptsMother.empty();

        LockoutStateRequest updated = request.withAttempts(empty);

        assertThat(updated).usingRecursiveComparison().ignoringFields("attempts").isEqualTo(request);
        assertThat(updated.getAttempts()).isEqualTo(empty);
    }

    @Test
    void shouldReturnTrueIfTimestampIsBeforeNow() {
        Instant timestamp = Instant.now();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .timestamp(timestamp)
                .build();
        Instant now = timestamp.plusMillis(1);

        boolean isBefore = request.isBefore(now);

        assertThat(isBefore).isTrue();
    }

    @Test
    void shouldReturnFalseIfTimestampIsAfterNow() {
        Instant timestamp = Instant.now();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .timestamp(timestamp)
                .build();
        Instant now = timestamp.minusMillis(1);

        boolean isBefore = request.isBefore(now);

        assertThat(isBefore).isFalse();
    }

    @Test
    void shouldRemoveAttempts() {
        Attempt attempt1 = AttemptMother.withChannelId("channel-1");
        Attempt attempt2 = AttemptMother.withChannelId("channel-2");
        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(AttemptsMother.withAttempts(attempt1, attempt2))
                .build();

        LockoutStateRequest updated = request.removeAttempts(AttemptsMother.withAttempts(attempt1));

        assertThat(updated).usingRecursiveComparison().ignoringFields("attempts").isEqualTo(request);
        assertThat(updated.getAttempts()).containsExactly(attempt2);
    }

    @Test
    void shouldReturnMostRecentAttemptTimestamp() {
        Instant expectedTimestamp = Instant.now();
        LockoutStateRequest request = LockoutStateRequest.builder()
                .attempts(givenAttemptsWithMostRecentTimestamp(expectedTimestamp))
                .build();

        Optional<Instant> timestamp = request.getMostRecentAttemptTimestamp();

        assertThat(timestamp).contains(expectedTimestamp);
    }

    private Attempts givenAttemptsWithMostRecentTimestamp(Instant timestamp) {
        Attempts attempts = mock(Attempts.class);
        given(attempts.getMostRecentTimestamp()).willReturn(Optional.of(timestamp));
        return attempts;
    }

}
