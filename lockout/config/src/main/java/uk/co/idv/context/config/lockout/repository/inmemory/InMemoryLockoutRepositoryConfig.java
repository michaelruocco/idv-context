package uk.co.idv.context.config.lockout.repository.inmemory;

import uk.co.idv.context.adapter.repository.InMemoryAttemptRepository;
import uk.co.idv.context.adapter.repository.InMemoryLockoutPolicyRepository;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyRepository;

public class InMemoryLockoutRepositoryConfig implements LockoutRepositoryConfig {

    private final LockoutPolicyRepository policyRepository = new InMemoryLockoutPolicyRepository();
    private final AttemptRepository attemptRepository = new InMemoryAttemptRepository();

    @Override
    public LockoutPolicyRepository policyRepository() {
        return policyRepository;
    }

    @Override
    public AttemptRepository attemptRepository() {
        return attemptRepository;
    }

}
