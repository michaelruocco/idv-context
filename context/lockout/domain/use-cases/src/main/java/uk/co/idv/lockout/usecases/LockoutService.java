package uk.co.idv.lockout.usecases;

import lombok.Builder;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;
import uk.co.idv.lockout.usecases.state.RecordAttempt;
import uk.co.idv.lockout.usecases.state.LoadLockoutState;
import uk.co.idv.lockout.usecases.state.ResetLockoutState;
import uk.co.idv.lockout.usecases.state.ValidateLockoutState;

@Builder
public class LockoutService {

    private final RecordAttempt recordAttempt;
    private final LoadLockoutState load;
    private final ValidateLockoutState validate;
    private final ResetLockoutState reset;

    public LockoutState loadState(LockoutRequest request) {
        return load.load(request);
    }

    public LockoutState resetState(LockoutRequest request) {
        return reset.reset(request);
    }

    public LockoutState recordAttemptIfRequired(RecordAttemptRequest request) {
        return recordAttempt.recordIfRequired(request);
    }

    public LockoutState loadAndValidateState(LockoutRequest request) {
        LockoutState state = loadState(request);
        validate.validate(state);
        return state;
    }

}
