package uk.co.idv.context.adapter.repository;

import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.Policy;
import uk.co.idv.context.usecases.policy.PolicyRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryLockoutPolicyRepository<T extends Policy> implements PolicyRepository<T> {

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

}
