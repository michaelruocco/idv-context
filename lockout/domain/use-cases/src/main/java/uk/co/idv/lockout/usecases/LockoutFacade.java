package uk.co.idv.lockout.usecases;

import lombok.Builder;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;
import uk.co.idv.lockout.entities.LockoutRequest;
import uk.co.idv.lockout.entities.policy.LockoutState;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;
import uk.co.idv.identity.usecases.identity.find.FindIdentity;

@Builder
public class LockoutFacade {

    private final FindIdentity findIdentity;
    private final LockoutService lockoutService;
    private final AliasFactory aliasFactory;
    private final ExternalLockoutRequestConverter converter;

    public Aliases toAliases(String type, String value) {
        return new DefaultAliases(toAlias(type, value));
    }

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
        Identity identity = findIdentity.find(request.getAliases());
        return converter.toLockoutRequest(request, identity.getIdvId());
    }

}
