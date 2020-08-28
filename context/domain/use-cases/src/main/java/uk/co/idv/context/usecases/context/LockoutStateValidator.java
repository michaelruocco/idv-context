package uk.co.idv.context.usecases.context;

import lombok.Builder;
import uk.co.idv.context.entities.context.create.ContextLockoutRequest;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.usecases.lockout.LockoutService;

import java.time.Clock;

@Builder
public class LockoutStateValidator {

    private final Clock clock;
    private final LockoutService lockoutService;

    public LockoutState validateLockoutState(IdentityCreateContextRequest request) {
        LockoutRequest lockoutRequest = ContextLockoutRequest.builder()
                .identityRequest(request)
                .timestamp(clock.instant())
                .build();
        return lockoutService.loadAndValidateState(lockoutRequest);
    }
}
