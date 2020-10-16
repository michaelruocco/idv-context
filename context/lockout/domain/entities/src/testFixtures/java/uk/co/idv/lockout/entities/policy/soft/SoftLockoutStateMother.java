package uk.co.idv.lockout.entities.policy.soft;

import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutState.SoftLockoutStateBuilder;


public interface SoftLockoutStateMother {

    static SoftLockoutState build() {
        return builder().build();
    }

    static SoftLockoutStateBuilder builder() {
        Attempt attempt = AttemptMother.unsuccessful();
        return SoftLockoutState.builder()
                .lock(SoftLockMother.startingAt(attempt.getTimestamp()))
                .attempts(AttemptsMother.withAttempts(attempt));
    }

}
