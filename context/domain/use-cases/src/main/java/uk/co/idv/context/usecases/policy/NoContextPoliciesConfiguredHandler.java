package uk.co.idv.context.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.NoPoliciesConfiguredHandler;

public class NoContextPoliciesConfiguredHandler
        implements NoPoliciesConfiguredHandler<NoContextPoliciesConfiguredException> {

    @Override
    public NoContextPoliciesConfiguredException toException(PolicyRequest request) {
        return new NoContextPoliciesConfiguredException(request);
    }

}
