package uk.co.idv.context.adapter.repository;

import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RedisPolicyRespository<T extends Policy> implements PolicyRepository<T> {

    private final Class<T> type;
    private final JsonConverter converter;
    private final Map<UUID, String> policies;

    @Override
    public void save(T policy) {
        String json = converter.toJson(policy);
        policies.put(policy.getId(), json);
    }

    @Override
    public Optional<T> load(UUID id) {
        return Optional.ofNullable(policies.get(id))
                .map(this::toObject);
    }

    @Override
    public Policies<T> loadAll() {
        return new Policies<>(policies.values().stream()
                .map(this::toObject)
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(UUID id) {
        policies.remove(id);
    }

    private T toObject(String json) {
        return converter.toObject(json, type);
    }

}
