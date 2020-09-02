package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.NoPoliciesConfiguredException;

public class NoLockoutPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoLockoutPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
