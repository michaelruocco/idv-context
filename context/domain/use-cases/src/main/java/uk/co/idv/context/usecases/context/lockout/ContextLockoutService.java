package uk.co.idv.context.usecases.context.lockout;

import lombok.Builder;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.ContextLockoutRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.lockout.ContextRecordAttemptRequest;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.usecases.LockoutService;

import java.time.Clock;

@Builder
public class ContextLockoutService {

    private final Clock clock;
    private final LockoutService lockoutService;

    public LockoutState validateLockoutState(Context context) {
        return validateLockoutState(context.getRequest());
    }

    public LockoutState validateLockoutState(ServiceCreateContextRequest request) {
        LockoutRequest lockoutRequest = ContextLockoutRequest.builder()
                .contextRequest(request)
                .timestamp(clock.instant())
                .build();
        return lockoutService.loadAndValidateState(lockoutRequest);
    }

    public LockoutState recordAttemptIfRequired(ContextRecordAttemptRequest request) {
        return lockoutService.recordAttemptIfRequired(request);
    }

}
