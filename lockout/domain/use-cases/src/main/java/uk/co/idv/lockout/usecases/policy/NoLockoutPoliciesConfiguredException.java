package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.NoPoliciesConfiguredException;

public class NoLockoutPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoLockoutPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
