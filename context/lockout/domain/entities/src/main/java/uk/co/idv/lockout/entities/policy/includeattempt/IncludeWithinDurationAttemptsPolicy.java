package uk.co.idv.lockout.entities.policy.includeattempt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Builder
@Data
public class IncludeWithinDurationAttemptsPolicy implements IncludeAttemptsPolicy {

    public static final String TYPE = "within-duration";

    @Getter(AccessLevel.NONE)
    private final Clock clock;
    private final Duration duration;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Attempts apply(Attempts attempts) {
        Instant end = clock.instant();
        Instant start = end.minus(duration);
        return attempts.occurredBetween(start, end);
    }

}
