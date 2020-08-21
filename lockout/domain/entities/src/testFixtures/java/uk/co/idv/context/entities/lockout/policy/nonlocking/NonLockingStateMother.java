package uk.co.idv.context.entities.lockout.policy.nonlocking;

import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;


public interface NonLockingStateMother {

    static NonLockingState build() {
        return new NonLockingState(AttemptsMother.withNumberOfAttempts(1));
    }

}
