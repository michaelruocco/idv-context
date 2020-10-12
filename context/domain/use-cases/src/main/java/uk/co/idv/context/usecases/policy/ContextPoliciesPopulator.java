package uk.co.idv.context.usecases.policy;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.policy.usecases.policy.PoliciesPopulator;

public class ContextPoliciesPopulator extends PoliciesPopulator<ContextPolicy> {

    public ContextPoliciesPopulator(ContextPolicyService service) {
        super(service);
    }

}
