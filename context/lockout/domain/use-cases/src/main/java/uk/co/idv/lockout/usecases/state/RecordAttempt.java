package uk.co.idv.lockout.usecases.state;

import lombok.Builder;
import uk.co.idv.lockout.entities.LockedOutException;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;
import uk.co.idv.lockout.usecases.attempt.SaveAttempts;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

@Builder
public class RecordAttempt {

    private final LockoutPolicyService policyService;
    private final ResetLockoutState reset;
    private final SaveAttempts save;
    private final LoadLockoutState load;

    public LockoutState recordIfRequired(RecordAttemptRequest request) {
        LockoutPolicy policy = policyService.loadHighestPriority(request);
        if (policy.shouldRecordAttempt(request)) {
            return recordIfRequired(request, policy);
        }
        return load.load(request, policy);
    }

    private LockoutState recordIfRequired(RecordAttemptRequest request, LockoutPolicy policy) {
        Attempt attempt = request.getAttempt();
        if (attempt.isSuccessful()) {
            return reset.reset(request, policy);
        }
        return recordFailedAttempt(request, policy);
    }

    private LockoutState recordFailedAttempt(RecordAttemptRequest request, LockoutPolicy policy) {
        LockoutState state = load.load(request, policy);
        if (state.isLocked()) {
            throw new LockedOutException(state);
        }
        Attempts attempts = save.save(request.getAttempt(), state.getAttempts());
        return policy.calculateState(request, attempts);
    }

}
