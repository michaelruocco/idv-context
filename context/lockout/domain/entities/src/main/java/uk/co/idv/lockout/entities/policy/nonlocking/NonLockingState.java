package uk.co.idv.lockout.entities.policy.nonlocking;

import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.policy.unlocked.UnlockedState;

public class NonLockingState extends UnlockedState {

    public NonLockingState(Attempts attempts) {
        super(attempts, "non locking policy");
    }

}
