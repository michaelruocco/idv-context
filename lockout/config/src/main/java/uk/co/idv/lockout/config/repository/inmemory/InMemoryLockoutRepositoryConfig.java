package uk.co.idv.lockout.config.repository.inmemory;

import uk.co.idv.lockout.adapter.repository.InMemoryAttemptRepository;
import uk.co.idv.lockout.adapter.repository.InMemoryLockoutPolicyRepository;
import uk.co.idv.lockout.config.repository.LockoutRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;

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
