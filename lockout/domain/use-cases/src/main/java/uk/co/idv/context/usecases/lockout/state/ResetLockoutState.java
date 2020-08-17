package uk.co.idv.context.usecases.lockout.state;

import lombok.Builder;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.usecases.lockout.attempt.AttemptsRepository;
import uk.co.idv.context.usecases.lockout.attempt.LoadAttempts;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;

@Builder
public class ResetLockoutState {

    private final LoadAttempts loadAttempts;
    private final LockoutPolicyService policyService;
    private final AttemptsRepository repository;

    public LockoutState reset(LockoutRequest request) {
        LockoutPolicy policy = policyService.loadHighestPriority(request);
        return reset(request, policy);
    }

    public LockoutState reset(LockoutRequest request, LockoutPolicy policy) {
        Attempts attempts = loadAttempts.load(request.getIdvId());
        return policy.resetState(request, attempts);
    }

}
