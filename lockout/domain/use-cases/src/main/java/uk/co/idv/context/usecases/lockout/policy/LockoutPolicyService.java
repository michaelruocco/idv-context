package uk.co.idv.context.usecases.lockout.policy;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.usecases.policy.PolicyService;
import uk.co.idv.context.usecases.policy.create.CreatePolicy;
import uk.co.idv.context.usecases.policy.delete.DeletePolicy;
import uk.co.idv.context.usecases.policy.load.LoadPolicy;
import uk.co.idv.context.usecases.policy.update.UpdatePolicy;

public class LockoutPolicyService extends PolicyService<LockoutPolicy, NoLockoutPoliciesConfiguredException> {

    public LockoutPolicyService(LockoutPolicyRepository repository) {
        super(new CreatePolicy<>(repository),
                new UpdatePolicy<>(repository),
                new LoadPolicy<>(repository),
                new DeletePolicy<>(repository),
                new NoLockoutPoliciesConfiguredHandler()
        );
    }

}
