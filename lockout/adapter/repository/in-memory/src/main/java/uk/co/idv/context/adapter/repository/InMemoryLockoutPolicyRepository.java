package uk.co.idv.context.adapter.repository;

import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.usecases.lockout.LockoutPolicyRepository;

public class InMemoryLockoutPolicyRepository
        extends InMemoryPolicyRepository<LockoutPolicy>
        implements LockoutPolicyRepository {

    //intentionally blank

}
