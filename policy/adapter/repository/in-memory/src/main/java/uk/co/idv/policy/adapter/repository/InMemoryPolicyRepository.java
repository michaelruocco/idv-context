package uk.co.idv.policy.adapter.repository;

import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryPolicyRepository<T extends Policy> implements PolicyRepository<T> {

    private final Map<UUID, T> policies = new HashMap<>();

    @Override
    public void save(T policy) {
        policies.put(policy.getId(), policy);
    }

    @Override
    public Optional<T> load(UUID id) {
        return Optional.ofNullable(policies.get(id));
    }

    @Override
    public Policies<T> loadAll() {
        return new Policies<>(policies.values());
    }

    @Override
    public void delete(UUID id) {
        policies.remove(id);
    }

}
