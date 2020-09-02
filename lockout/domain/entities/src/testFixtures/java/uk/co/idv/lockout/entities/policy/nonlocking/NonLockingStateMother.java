package uk.co.idv.lockout.entities.policy.nonlocking;

import uk.co.idv.lockout.entities.attempt.AttemptsMother;


public interface NonLockingStateMother {

    static NonLockingState build() {
        return new NonLockingState(AttemptsMother.withNumberOfAttempts(1));
    }

}
