package uk.co.idv.lockout.adapter.json.error.lockedout;

import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateMother;

public interface LockedOutErrorMother {

    static LockedOutError lockedOutError() {
        return lockedOutError(HardLockoutStateMother.build());
    }

    static LockedOutError lockedOutError(LockoutState state) {
        return new LockedOutError(state);
    }

}
