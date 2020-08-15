package uk.co.idv.context.entities.lockout.policy;

import uk.co.idv.context.entities.lockout.attempt.AttemptMother;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;

public interface LockoutStateRequestMother {

    static LockoutStateRequest build() {
        return builder().build();
    }

    static LockoutStateRequest withOneAttempt() {
        return withNumberOfAttempts(1);
    }

    static LockoutStateRequest withNumberOfAttempts(int numberOfAttempts) {
        return builder()
                .attempts(AttemptsMother.withNumberOfAttempts(numberOfAttempts))
                .build();
    }

    static LockoutStateRequest.LockoutStateRequestBuilder builder() {
        return LockoutStateRequest.builder()
                .newAttempt(AttemptMother.build())
                .attempts(AttemptsMother.build());
    }

}
