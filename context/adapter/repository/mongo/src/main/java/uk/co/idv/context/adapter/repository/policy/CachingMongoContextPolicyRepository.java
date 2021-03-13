package uk.co.idv.context.adapter.repository.policy;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.policy.usecases.policy.CachingRepositoryDecorator;

public class CachingMongoContextPolicyRepository extends CachingRepositoryDecorator<ContextPolicy> implements ContextPolicyRepository {

    public CachingMongoContextPolicyRepository(ContextPolicyRepository repository) {
        super(repository);
    }

}
