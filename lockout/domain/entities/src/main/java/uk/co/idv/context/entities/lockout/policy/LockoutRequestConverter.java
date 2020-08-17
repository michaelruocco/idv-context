package uk.co.idv.context.entities.lockout.policy;

import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.LockoutRequest;

public class LockoutRequestConverter {

    public LockoutStateRequest toLockoutStateRequest(LockoutRequest request, Attempts attempts) {
        return LockoutStateRequest.builder()
                .alias(request.getAlias())
                .timestamp(request.getTimestamp())
                .attempts(attempts)
                .build();
    }

}
