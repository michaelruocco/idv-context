package uk.co.idv.lockout.usecases.state;

import uk.co.idv.lockout.entities.policy.LockoutState;

public class ValidateLockoutState {

    public void validate(LockoutState state) {
        if (state.isLocked()) {
            throw new LockedOutException(state);
        }
    }

}
