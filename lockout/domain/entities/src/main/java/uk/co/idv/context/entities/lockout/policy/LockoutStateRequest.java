package uk.co.idv.context.entities.lockout.policy;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

import java.time.Instant;

@Builder
@Data
public class LockoutStateRequest {

    private final Instant timestamp;
    private final Alias alias;

    @With
    private final Attempts attempts;

    public int getNumberOfAttempts() {
        return attempts.size();
    }

    public Alias getAlias() {
        return alias;
    }

    public Instant getMostRecentAttemptTimestamp() {
        return attempts.getMostRecentTimestamp();
    }

    public LockoutStateRequest removeAttempts(Attempts attemptsToRemove) {
        return withAttempts(attempts.remove(attemptsToRemove));
    }

    public boolean isBefore(Instant instant) {
        return timestamp.isBefore(instant);
    }

}
