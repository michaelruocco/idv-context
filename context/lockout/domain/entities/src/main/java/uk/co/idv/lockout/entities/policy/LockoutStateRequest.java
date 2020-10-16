package uk.co.idv.lockout.entities.policy;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.lockout.entities.attempt.Attempts;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class LockoutStateRequest {

    private final Instant timestamp;
    private final Aliases aliases;

    @With
    private final Attempts attempts;

    public int getNumberOfAttempts() {
        return attempts.size();
    }

    public Optional<Instant> getMostRecentAttemptTimestamp() {
        return attempts.getMostRecentTimestamp();
    }

    public LockoutStateRequest removeAttempts(Attempts attemptsToRemove) {
        return withAttempts(attempts.remove(attemptsToRemove));
    }

    public boolean isBefore(Instant instant) {
        return timestamp.isBefore(instant);
    }

}
