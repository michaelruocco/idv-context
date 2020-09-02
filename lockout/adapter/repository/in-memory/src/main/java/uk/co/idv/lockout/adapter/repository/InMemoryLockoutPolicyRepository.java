package uk.co.idv.lockout.adapter.repository;

import uk.co.idv.context.adapter.repository.InMemoryPolicyRepository;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;

public class InMemoryLockoutPolicyRepository
        extends InMemoryPolicyRepository<LockoutPolicy>
        implements LockoutPolicyRepository {

    //intentionally blank

}
