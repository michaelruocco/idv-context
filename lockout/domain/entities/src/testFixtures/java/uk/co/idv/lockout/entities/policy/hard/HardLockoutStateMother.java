package uk.co.idv.lockout.entities.policy.hard;

import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutState.HardLockoutStateBuilder;


public interface HardLockoutStateMother {

    static HardLockoutState build() {
        return builder().build();
    }

    static HardLockoutStateBuilder builder() {
        return HardLockoutState.builder()
                .maxNumberOfAttempts(3)
                .attempts(AttemptsMother.withNumberOfAttempts(1));
    }

}
