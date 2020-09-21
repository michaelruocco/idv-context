package uk.co.idv.context.config.repository.inmemory;

import uk.co.idv.context.adapter.repository.InMemoryContextPolicyRepository;
import uk.co.idv.context.adapter.repository.InMemoryContextRepository;
import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;

public class InMemoryContextRepositoryConfig implements ParentContextRepositoryConfig {

    private final ContextPolicyRepository policyRepository = new InMemoryContextPolicyRepository();
    private final ContextRepository contextRepository = new InMemoryContextRepository();

    @Override
    public ContextPolicyRepository policyRepository() {
        return policyRepository;
    }

    @Override
    public ContextRepository contextRepository() {
        return contextRepository;
    }

}
