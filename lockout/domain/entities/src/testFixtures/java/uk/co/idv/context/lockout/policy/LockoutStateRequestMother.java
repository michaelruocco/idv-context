package uk.co.idv.context.lockout.policy;

import uk.co.idv.context.lockout.attempt.VerificationAttemptMother;
import uk.co.idv.context.lockout.attempt.VerificationAttemptsMother;

public interface LockoutStateRequestMother {

    static LockoutStateRequest build() {
        return builder().build();
    }

    static LockoutStateRequest.LockoutStateRequestBuilder builder() {
        return LockoutStateRequest.builder()
                .attempt(VerificationAttemptMother.build())
                .attempts(VerificationAttemptsMother.build());
    }

}
