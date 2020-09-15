package uk.co.idv.context.usecases.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.policy.usecases.policy.PolicyService;
import uk.co.idv.policy.usecases.policy.create.CreatePolicy;
import uk.co.idv.policy.usecases.policy.delete.DeletePolicy;
import uk.co.idv.policy.usecases.policy.load.LoadPolicy;
import uk.co.idv.policy.usecases.policy.update.UpdatePolicy;

@Slf4j
public class ContextPolicyService extends PolicyService<ContextPolicy, NoContextPoliciesConfiguredException> {

    public ContextPolicyService(ContextPolicyRepository repository) {
        super(new CreatePolicy<>(repository),
                new UpdatePolicy<>(repository),
                new LoadPolicy<>(repository),
                new DeletePolicy<>(repository),
                new NoContextPoliciesConfiguredHandler());
    }

}
