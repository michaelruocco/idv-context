package uk.co.idv.context.lockout.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.lockout.attempt.VerificationAttempts;

@Builder
@Data
public class LockoutStateRequest {

    private final VerificationAttempt attempt;
    private final VerificationAttempts attempts;

    public Alias getAlias() {
        return attempt.getAlias();
    }

}
