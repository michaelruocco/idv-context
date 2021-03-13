package uk.co.idv.lockout.adapter.repository.policy;

import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.idv.policy.usecases.policy.CachingRepositoryDecorator;

public class CachingMongoLockoutPolicyRepository extends CachingRepositoryDecorator<LockoutPolicy> implements LockoutPolicyRepository {

    public CachingMongoLockoutPolicyRepository(LockoutPolicyRepository repository) {
        super(repository);
    }

}
