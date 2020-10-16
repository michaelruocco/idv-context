package uk.co.idv.lockout.usecases.policy;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.policy.usecases.policy.PolicyService;
import uk.co.idv.policy.usecases.policy.create.CreatePolicy;
import uk.co.idv.policy.usecases.policy.delete.DeletePolicy;
import uk.co.idv.policy.usecases.policy.load.LoadPolicy;
import uk.co.idv.policy.usecases.policy.update.UpdatePolicy;

public class LockoutPolicyService extends PolicyService<LockoutPolicy> {

    public LockoutPolicyService(LockoutPolicyRepository repository) {
        super(new CreatePolicy<>(repository),
                new UpdatePolicy<>(repository),
                new LoadPolicy<>(repository),
                new DeletePolicy<>(repository),
                new NoLockoutPoliciesConfiguredHandler()
        );
    }

}
