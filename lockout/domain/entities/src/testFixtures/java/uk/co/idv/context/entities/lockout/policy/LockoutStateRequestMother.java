package uk.co.idv.context.entities.lockout.policy;

import uk.co.idv.context.entities.lockout.attempt.VerificationAttemptMother;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttemptsMother;

public interface LockoutStateRequestMother {

    static LockoutStateRequest build() {
        return builder().build();
    }

    static LockoutStateRequest withOneAttempt() {
        return withNumberOfAttempts(1);
    }

    static LockoutStateRequest withNumberOfAttempts(int numberOfAttempts) {
        return builder()
                .attempts(VerificationAttemptsMother.withNumberOfAttempts(numberOfAttempts))
                .build();
    }

    static LockoutStateRequest.LockoutStateRequestBuilder builder() {
        return LockoutStateRequest.builder()
                .newAttempt(VerificationAttemptMother.build())
                .attempts(VerificationAttemptsMother.build());
    }

}
