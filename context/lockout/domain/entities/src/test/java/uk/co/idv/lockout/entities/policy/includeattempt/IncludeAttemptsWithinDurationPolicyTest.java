package uk.co.idv.lockout.entities.policy.includeattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class IncludeAttemptsWithinDurationPolicyTest {

    private static final Instant NOW = Instant.now();

    private final Clock clock = Clock.fixed(NOW, ZoneId.systemDefault());
    private final Duration duration = Duration.ofMinutes(5);

    private final IncludeAttemptsWithinDurationPolicy filter = IncludeAttemptsWithinDurationPolicy.builder()
            .clock(clock)
            .duration(duration)
            .build();

    @Test
    void shouldReturnType() {
        assertThat(filter.getType()).isEqualTo("within-duration");
    }

    @Test
    void shouldNotIncludeAttemptsThatOccurredAtCutoffOrBefore() {
        Instant cutoff = NOW.minus(duration);
        Attempt atCutoff = AttemptMother.withTimestamp(cutoff);
        Attempt beforeCutoff = AttemptMother.withTimestamp(cutoff.minusMillis(1));
        Attempts attempts = AttemptsMother.withAttempts(atCutoff, beforeCutoff);

        Attempts filtered = filter.apply(attempts);

        assertThat(filtered).isEmpty();
    }

    @Test
    void shouldNotIncludeAttemptsThatOccurredNowOrInFuture() {
        Attempt atNow = AttemptMother.withTimestamp(NOW);
        Attempt inFuture = AttemptMother.withTimestamp(NOW.plusMillis(1));
        Attempts attempts = AttemptsMother.withAttempts(atNow, inFuture);

        Attempts filtered = filter.apply(attempts);

        assertThat(filtered).isEmpty();
    }

    @Test
    void shouldIncludeAttemptsThatOccurredBetweenNowAndCutoff() {
        Instant cutoff = NOW.minus(duration);
        Attempt included = AttemptMother.withTimestamp(cutoff.plusMillis(1));
        Attempts attempts = AttemptsMother.withAttempts(included);

        Attempts filtered = filter.apply(attempts);

        assertThat(filtered).containsExactly(included);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(filter.getDuration()).isEqualTo(duration);
    }

}
