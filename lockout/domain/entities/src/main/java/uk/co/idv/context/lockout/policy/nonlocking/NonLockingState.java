package uk.co.idv.context.lockout.policy.nonlocking;

import uk.co.idv.context.lockout.attempt.VerificationAttempts;
import uk.co.idv.context.lockout.policy.unlocked.UnlockedState;

public class NonLockingState extends UnlockedState {

    public NonLockingState(VerificationAttempts attempts) {
        super(attempts, "non locking policy");
    }

}
