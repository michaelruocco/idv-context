package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.context.entities.context.create.ContextLockoutRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.usecases.LockoutService;

import java.time.Clock;

@Builder
public class LockoutStateValidator {

    private final Clock clock;
    private final LockoutService lockoutService;

    public LockoutState validateLockoutState(DefaultCreateContextRequest request) {
        LockoutRequest lockoutRequest = ContextLockoutRequest.builder()
                .identityRequest(request)
                .timestamp(clock.instant())
                .build();
        return lockoutService.loadAndValidateState(lockoutRequest);
    }
}
