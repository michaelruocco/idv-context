package uk.co.idv.lockout.usecases.state;

import lombok.Builder;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.ResetResult;
import uk.co.idv.lockout.usecases.attempt.LoadAttempts;
import uk.co.idv.lockout.usecases.attempt.SaveAttempts;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

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
