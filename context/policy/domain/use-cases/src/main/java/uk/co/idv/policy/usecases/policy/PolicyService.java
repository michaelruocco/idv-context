package uk.co.idv.policy.usecases.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.PolicyRequest;
import uk.co.idv.policy.usecases.policy.create.CreatePolicy;
import uk.co.idv.policy.usecases.policy.delete.DeletePolicy;
import uk.co.idv.policy.usecases.policy.load.LoadPolicy;
import uk.co.idv.policy.usecases.policy.update.UpdatePolicy;

import java.util.UUID;

@RequiredArgsConstructor
public class PolicyService<P extends Policy> {

    private final CreatePolicy<P> create;
    private final UpdatePolicy<P> update;
    private final LoadPolicy<P> load;
    private final DeletePolicy<P> delete;
    private final NoPoliciesConfiguredHandler noPoliciesConfiguredHandler;

    public void create(P policy) {
        create.create(policy);
    }

    public void update(P policy) {
        update.update(policy);
    }

    public void delete(UUID id) {
        delete.delete(id);
    }

    public P load(UUID id) {
        return load.load(id);
    }

    public P loadHighestPriority(PolicyRequest request) {
        Policies<P> policies = load(request);
        if (policies.isEmpty()) {
            throw noPoliciesConfiguredHandler.toException(request);
        }
        return policies.getHighestPriority();
    }

    public Policies<P> load(PolicyRequest request) {
        return load.load(request);
    }

    public Policies<P> load(PolicyKey key) {
        return load.load(key);
    }

    public Policies<P> loadAll() {
        return load.loadAll();
    }

}
