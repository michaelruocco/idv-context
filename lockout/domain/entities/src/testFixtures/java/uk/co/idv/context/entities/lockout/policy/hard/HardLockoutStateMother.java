package uk.co.idv.context.entities.lockout.policy.hard;

import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutState.HardLockoutStateBuilder;


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
