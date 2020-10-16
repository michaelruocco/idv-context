package uk.co.idv.lockout.usecases.state;

import lombok.Builder;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.usecases.attempt.LoadAttempts;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

@Builder
public class LoadLockoutState {

    private final LockoutPolicyService policyService;
    private final LoadAttempts loadAttempts;

    public LockoutState load(LockoutRequest request) {
        LockoutPolicy policy = policyService.loadHighestPriority(request);
        return load(request, policy);
    }

    public LockoutState load(LockoutRequest request, LockoutPolicy policy) {
        Attempts attempts = loadAttempts.load(request.getIdvId());
        return policy.calculateState(request, attempts);
    }

}
