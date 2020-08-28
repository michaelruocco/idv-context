package uk.co.idv.context.entities.lockout.policy;

import lombok.Data;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.LockoutRequest;

@Data
public class LockoutRequestConverter {

    public LockoutStateRequest toLockoutStateRequest(LockoutRequest request, Attempts attempts) {
        return LockoutStateRequest.builder()
                .aliases(request.getAliases())
                .timestamp(request.getTimestamp())
                .attempts(attempts)
                .build();
    }

}
