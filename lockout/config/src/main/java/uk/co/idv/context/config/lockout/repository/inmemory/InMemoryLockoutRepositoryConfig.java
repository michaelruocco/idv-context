package uk.co.idv.context.config.lockout.repository.inmemory;

import uk.co.idv.context.adapter.repository.InMemoryAttemptsRepository;
import uk.co.idv.context.adapter.repository.InMemoryLockoutPolicyRepository;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.usecases.lockout.attempt.AttemptsRepository;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyRepository;

public class InMemoryLockoutRepositoryConfig implements LockoutRepositoryConfig {

    private final LockoutPolicyRepository policyRepository = new InMemoryLockoutPolicyRepository();
    private final AttemptsRepository attemptsRepository = new InMemoryAttemptsRepository();

    @Override
    public LockoutPolicyRepository policyRepository() {
        return policyRepository;
    }

    @Override
    public AttemptsRepository attemptsRepository() {
        return attemptsRepository;
    }

}
