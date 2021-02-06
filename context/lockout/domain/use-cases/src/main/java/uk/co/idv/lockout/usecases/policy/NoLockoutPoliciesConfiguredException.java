package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.policy.entities.policy.NoPoliciesConfiguredException;
import uk.co.idv.policy.entities.policy.PolicyRequest;

public class NoLockoutPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoLockoutPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
