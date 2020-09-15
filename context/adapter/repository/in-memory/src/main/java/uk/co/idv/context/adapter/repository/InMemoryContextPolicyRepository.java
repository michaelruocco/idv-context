package uk.co.idv.context.adapter.repository;

import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.policy.adapter.repository.InMemoryPolicyRepository;

public class InMemoryContextPolicyRepository
        extends InMemoryPolicyRepository<ContextPolicy>
        implements ContextPolicyRepository {

    //intentionally blank

}
