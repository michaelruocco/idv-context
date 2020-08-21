package uk.co.idv.context.usecases.lockout;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasFactory;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequest;
import uk.co.idv.context.entities.lockout.policy.LockoutState;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

@Builder
public class LockoutFacade {

    private final FindIdentity findIdentity;
    private final LockoutService lockoutService;
    private final AliasFactory aliasFactory;
    private final ExternalLockoutRequestConverter converter;

    public Alias toAlias(String type, String value) {
        return aliasFactory.build(type, value);
    }

    public LockoutState recordAttempt(RecordAttemptRequest request) {
        findIdentity.find(request.getIdvId());
        return lockoutService.recordAttemptIfRequired(request);
    }

    public LockoutState loadState(ExternalLockoutRequest externalRequest) {
        LockoutRequest lockoutRequest = toLockoutRequest(externalRequest);
        return lockoutService.loadAndValidateState(lockoutRequest);
    }

    public LockoutState resetState(ExternalLockoutRequest externalRequest) {
        LockoutRequest lockoutRequest = toLockoutRequest(externalRequest);
        return lockoutService.resetState(lockoutRequest);
    }

    private LockoutRequest toLockoutRequest(ExternalLockoutRequest request) {
        Identity identity = findIdentity.find(request.getAlias());
        return converter.toLockoutRequest(request, identity.getIdvId());
    }

}
