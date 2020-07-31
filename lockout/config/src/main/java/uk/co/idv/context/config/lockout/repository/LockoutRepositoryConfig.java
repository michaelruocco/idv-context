package uk.co.idv.context.config.lockout.repository;

import uk.co.idv.context.usecases.lockout.LockoutPolicyRepository;

public interface LockoutRepositoryConfig {

    LockoutPolicyRepository policyRepository();

}
