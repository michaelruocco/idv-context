package uk.co.idv.context.entities.lockout.policy.nonlocking;

import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.policy.unlocked.UnlockedState;

public class NonLockingState extends UnlockedState {

    public NonLockingState(Attempts attempts) {
        super(attempts, "non locking policy");
    }

}
