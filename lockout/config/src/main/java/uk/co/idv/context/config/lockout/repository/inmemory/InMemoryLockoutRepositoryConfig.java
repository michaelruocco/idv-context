package uk.co.idv.context.config.lockout.repository.inmemory;

import uk.co.idv.context.adapter.repository.InMemoryLockoutPolicyRepository;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyRepository;

public class InMemoryLockoutRepositoryConfig implements LockoutRepositoryConfig {

    private final LockoutPolicyRepository repository = new InMemoryLockoutPolicyRepository();

    @Override
    public LockoutPolicyRepository policyRepository() {
        return repository;
    }

}
