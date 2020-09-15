package uk.co.idv.context.usecases.policy;

import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.NoPoliciesConfiguredException;

public class NoContextPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoContextPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
