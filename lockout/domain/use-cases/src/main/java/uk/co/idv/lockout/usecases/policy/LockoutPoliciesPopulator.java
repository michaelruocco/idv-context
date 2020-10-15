package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.usecases.policy.PoliciesPopulator;

public class LockoutPoliciesPopulator extends PoliciesPopulator<LockoutPolicy> {

    public LockoutPoliciesPopulator(LockoutPolicyService service) {
        super(service);
    }

}
