package uk.co.idv.context.usecases.lockout.policy;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.PolicyService;
import uk.co.idv.context.usecases.policy.NoPoliciesConfiguredException;
import uk.co.idv.context.usecases.policy.create.CreatePolicy;
import uk.co.idv.context.usecases.policy.delete.DeletePolicy;
import uk.co.idv.context.usecases.policy.load.LoadPolicy;
import uk.co.idv.context.usecases.policy.update.UpdatePolicy;

public class LockoutPolicyService extends PolicyService<LockoutPolicy> {

    public LockoutPolicyService(LockoutPolicyRepository repository) {
        super(new CreatePolicy<>(repository),
                new UpdatePolicy<>(repository),
                new LoadPolicy<>(repository),
                new DeletePolicy<>(repository)
        );
    }

    @Override
    public LockoutPolicy loadHighestPriority(PolicyRequest request) {
        try {
            return super.loadHighestPriority(request);
        } catch (NoPoliciesConfiguredException e) {
            throw new NoLockoutPoliciesConfiguredException(e.getRequest());
        }
    }

}
