package uk.co.idv.context.entities.lockout.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;

import java.time.Duration;
import java.time.Instant;

@Builder
@Data
public class LockoutStateRequest {

    private final VerificationAttempt newAttempt;
    private final VerificationAttempts attempts;

    public int getNumberOfAttempts() {
        return attempts.size();
    }

    public Alias getAlias() {
        return newAttempt.getAlias();
    }

    public Instant addToMostRecentAttemptTimestamp(Duration duration) {
        return attempts.getMostRecentTimestamp().plus(duration);
    }

    public boolean isNewAttemptBefore(Instant instant) {
        return newAttempt.getTimestamp().isBefore(instant);
    }

}
