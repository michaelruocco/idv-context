package uk.co.idv.context.usecases.lockout.policy;

import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.NoPoliciesConfiguredHandler;

public class NoLockoutPoliciesConfiguredHandler
        implements NoPoliciesConfiguredHandler<NoLockoutPoliciesConfiguredException> {

    @Override
    public NoLockoutPoliciesConfiguredException toException(PolicyRequest request) {
        return new NoLockoutPoliciesConfiguredException(request);
    }

}
