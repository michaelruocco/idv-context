package uk.co.idv.lockout.config.repository;

import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;

public interface LockoutPolicyRepositoryConfig {

    LockoutPolicyRepository policyRepository();

}
