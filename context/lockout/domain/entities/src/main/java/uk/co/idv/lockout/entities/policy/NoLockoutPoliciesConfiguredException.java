package uk.co.idv.lockout.entities.policy;

import uk.co.idv.policy.entities.policy.NoPoliciesConfiguredException;
import uk.co.idv.policy.entities.policy.PolicyRequest;

public class NoLockoutPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoLockoutPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
