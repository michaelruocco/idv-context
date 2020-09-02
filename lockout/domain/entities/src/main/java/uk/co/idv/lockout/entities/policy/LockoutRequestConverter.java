package uk.co.idv.lockout.entities.policy;

import lombok.Data;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.LockoutRequest;

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
