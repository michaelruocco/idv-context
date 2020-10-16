package uk.co.idv.lockout.usecases.state;

import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateMother;

public interface LockedOutExceptionMother {

    static LockedOutException build() {
        return new LockedOutException(HardLockoutStateMother.build());
    }

}
