package uk.co.idv.context.usecases.lockout.state;

import uk.co.idv.context.entities.lockout.policy.LockoutState;

public class ValidateLockoutState {

    public void validate(LockoutState state) {
        if (state.isLocked()) {
            throw new LockedOutException(state);
        }
    }

}
