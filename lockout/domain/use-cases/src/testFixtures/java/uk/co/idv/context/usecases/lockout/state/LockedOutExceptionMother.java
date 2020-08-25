package uk.co.idv.context.usecases.lockout.state;

import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateMother;

public interface LockedOutExceptionMother {

    static LockedOutException build() {
        return new LockedOutException(HardLockoutStateMother.build());
    }

}
