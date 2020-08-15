package uk.co.idv.context.config.lockout.repository;

import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyRepository;

public interface LockoutRepositoryConfig {

    LockoutPolicyRepository policyRepository();

}
