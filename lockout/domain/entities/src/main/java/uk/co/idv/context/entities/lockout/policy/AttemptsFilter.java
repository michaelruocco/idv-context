package uk.co.idv.context.entities.lockout.policy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempts;

@RequiredArgsConstructor
@Data
public class AttemptsFilter {

    private final PolicyKey key;

    public VerificationAttempts filter(LockoutStateRequest request) {
        VerificationAttempts attempts = request.getAttempts();
        VerificationAttempts applicable = attempts.applyingTo(key);
        if (key.hasAliasType()) {
            return applicable.with(request.getAlias());
        }
        return applicable;
    }

}
