package uk.co.idv.context.usecases.lockout;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

import java.time.Clock;

@Builder
public class LockoutFacade {

    private final FindIdentity findIdentity;
    private final LockoutService lockoutService;
    private final Clock clock;

    public LockoutState loadState(ExternalLockoutRequest externalRequest) {
        LockoutRequest lockoutRequest = toLockoutRequest(externalRequest);
        return lockoutService.loadState(lockoutRequest);
    }

    public LockoutState resetState(ExternalLockoutRequest externalRequest) {
        LockoutRequest lockoutRequest = toLockoutRequest(externalRequest);
        return lockoutService.resetState(lockoutRequest);
    }

    private LockoutRequest toLockoutRequest(ExternalLockoutRequest request) {
        //TODO replace with idv id lookup
        Identity identity = findIdentity.find(request.getAlias());
        return DefaultLockoutRequest.builder()
                .timestamp(clock.instant())
                .idvId(identity.getIdvId())
                .externalRequest(request)
                .build();
    }

}
