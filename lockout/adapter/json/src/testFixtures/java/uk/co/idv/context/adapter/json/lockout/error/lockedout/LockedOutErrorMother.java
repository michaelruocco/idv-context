package uk.co.idv.context.adapter.json.lockout.error.lockedout;

import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateMother;

public interface LockedOutErrorMother {

    static LockedOutError lockedOutError() {
        return lockedOutError(HardLockoutStateMother.build());
    }

    static LockedOutError lockedOutError(LockoutState state) {
        return new LockedOutError(state);
    }

}
