package uk.co.idv.context.usecases.policy;

import uk.co.idv.policy.entities.policy.NoPoliciesConfiguredException;
import uk.co.idv.policy.entities.policy.PolicyRequest;

public class NoContextPoliciesConfiguredException extends NoPoliciesConfiguredException {

    public NoContextPoliciesConfiguredException(PolicyRequest request) {
        super(request);
    }

}
