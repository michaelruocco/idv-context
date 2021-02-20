package uk.co.idv.lockout.usecases;

import lombok.Builder;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;

@Builder
public class LockoutFacade {

    private final FindIdentity findIdentity;
    private final LockoutService lockoutService;
    private final ExternalLockoutRequestConverter converter;

    public LockoutState loadState(ExternalLockoutRequest externalRequest) {
        LockoutRequest lockoutRequest = toLockoutRequest(externalRequest);
        return lockoutService.loadState(lockoutRequest);
    }

    public LockoutState resetState(ExternalLockoutRequest externalRequest) {
        LockoutRequest lockoutRequest = toLockoutRequest(externalRequest);
        return lockoutService.resetState(lockoutRequest);
    }

    private LockoutRequest toLockoutRequest(ExternalLockoutRequest request) {
        Identity identity = findIdentity.find(request.getAliases());
        return converter.toLockoutRequest(request, identity.getIdvId());
    }

}
