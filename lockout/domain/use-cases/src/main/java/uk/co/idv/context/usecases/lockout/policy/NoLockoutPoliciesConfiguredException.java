package uk.co.idv.context.usecases.lockout.policy;

import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.NoPoliciesConfiguredException;

public class NoLockoutPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoLockoutPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
