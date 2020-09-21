package uk.co.idv.context.config.repository;

import lombok.Builder;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;

@Builder
public class DefaultParentContextRepositoryConfig implements ParentContextRepositoryConfig {

    private final ContextRepositoryConfig contextRepositoryConfig;
    private final ContextPolicyRepositoryConfig policyRepositoryConfig;

    @Override
    public ContextPolicyRepository policyRepository() {
        return policyRepositoryConfig.policyRepository();
    }

    @Override
    public ContextRepository contextRepository() {
        return contextRepositoryConfig.contextRepository();
    }

}
