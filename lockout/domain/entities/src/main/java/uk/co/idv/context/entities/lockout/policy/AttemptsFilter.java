package uk.co.idv.context.entities.lockout.policy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.lockout.attempt.Attempts;

@RequiredArgsConstructor
@Data
public class AttemptsFilter {

    private final PolicyKey key;

    public Attempts filter(LockoutStateRequest request) {
        Attempts attempts = request.getAttempts();
        Attempts applicable = attempts.applyingTo(key);
        if (key.hasAliasType()) {
            return applicable.with(request.getAliases());
        }
        return applicable;
    }

}
