package uk.co.idv.context.usecases.lockout.state;

import lombok.Builder;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.policy.ResetResult;
import uk.co.idv.context.usecases.lockout.attempt.LoadAttempts;
import uk.co.idv.context.usecases.lockout.attempt.SaveAttempts;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;

@Builder
public class ResetLockoutState {

    private final LoadAttempts loadAttempts;
    private final LockoutPolicyService policyService;
    private final SaveAttempts saveAttempts;

    public LockoutState reset(LockoutRequest request) {
        LockoutPolicy policy = policyService.loadHighestPriority(request);
        return reset(request, policy);
    }

    public LockoutState reset(LockoutRequest request, LockoutPolicy policy) {
        Attempts attempts = loadAttempts.load(request.getIdvId());
        ResetResult result = policy.resetState(request, attempts);
        saveAttempts.save(attempts.remove(result.getAttemptsToRemove()));
        return result.getState();
    }

}
