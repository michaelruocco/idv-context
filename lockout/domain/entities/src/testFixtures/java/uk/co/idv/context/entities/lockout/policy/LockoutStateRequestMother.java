package uk.co.idv.context.entities.lockout.policy;

import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;

import java.time.Instant;

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
                .aliases(AliasesMother.defaultAliasOnly())
                .timestamp(Instant.parse("2019-09-27T09:35:15.612Z"))
                .attempts(AttemptsMother.build());
    }

}
