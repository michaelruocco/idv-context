package uk.co.idv.context.entities.lockout.policy.soft;

import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.attempt.AttemptMother;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockoutState.SoftLockoutStateBuilder;


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
