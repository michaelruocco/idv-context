package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.NoPoliciesConfiguredHandler;

public class NoLockoutPoliciesConfiguredHandler implements NoPoliciesConfiguredHandler {

    @Override
    public NoLockoutPoliciesConfiguredException toException(PolicyRequest request) {
        return new NoLockoutPoliciesConfiguredException(request);
    }

}
