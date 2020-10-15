package uk.co.idv.app.manual.adapter.repository;

import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.config.repository.inmemory.InMemoryIdentityRepositoryConfig;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.lockout.config.repository.LockoutRepositoryConfig;
import uk.co.idv.lockout.config.repository.inmemory.InMemoryLockoutRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;

public class InMemoryRepositoryAdapter implements RepositoryAdapter {

    private final IdentityRepositoryConfig identityRepositoryConfig = new InMemoryIdentityRepositoryConfig();
    private final ParentContextRepositoryConfig contextRepositoryConfig = new InMemoryContextRepositoryConfig();
    private final LockoutRepositoryConfig lockoutRepositoryConfig = new InMemoryLockoutRepositoryConfig();

    @Override
    public IdentityRepository getIdentityRepository() {
        return identityRepositoryConfig.identityRepository();
    }

    @Override
    public ContextRepository getContextRepository() {
        return contextRepositoryConfig.contextRepository();
    }

    @Override
    public ContextPolicyRepository getContextPolicyRepository() {
        return contextRepositoryConfig.policyRepository();
    }

    @Override
    public AttemptRepository getAttemptRepository() {
        return lockoutRepositoryConfig.attemptRepository();
    }

    @Override
    public LockoutPolicyRepository getLockoutPolicyRepository() {
        return lockoutRepositoryConfig.policyRepository();
    }

}
