package uk.co.idv.context.usecases.policy;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.PolicyRequest;
import uk.co.idv.context.usecases.policy.create.CreatePolicy;
import uk.co.idv.context.usecases.policy.load.LoadPolicy;
import uk.co.idv.context.usecases.policy.update.UpdatePolicy;

import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class PolicyFacade<T extends Policy> {

    private final CreatePolicy<T> create;
    private final UpdatePolicy<T> update;
    private final LoadPolicy<T> load;

    public void create(T policy) {
        create.create(policy);
    }

    public void update(T policy) {
        update.update(policy);
    }

    public T load(UUID id) {
        return load.load(id);
    }

    public T load(PolicyRequest request) {
        return load.load(request);
    }

    public Policies<T> load(PolicyKey key) {
        return load.load(key);
    }

    public Policies<T> loadAll() {
        return load.loadAll();
    }

}
