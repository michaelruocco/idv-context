package uk.co.idv.context.adapter.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@RequiredArgsConstructor
@Slf4j
public class RedisPolicyRepository<T extends Policy> implements PolicyRepository<T> {

    private final Class<T> type;
    private final JsonConverter converter;
    private final Map<UUID, String> policies;

    @Override
    public void save(T policy) {
        Instant start = Instant.now();
        try {
            String json = converter.toJson(policy);
            policies.put(policy.getId(), json);
        } finally {
            log.info("took {}ms to save policy with id {}",
                    millisBetweenNowAnd(start),
                    policy.getId());
        }
    }

    @Override
    public Optional<T> load(UUID id) {
        Instant start = Instant.now();
        try {
            return Optional.ofNullable(policies.get(id))
                    .map(this::toObject);
        } finally {
            log.info("took {}ms to load policy using id {}",
                    millisBetweenNowAnd(start),
                    id);
        }
    }

    @Override
    public Policies<T> loadAll() {
        Instant start = Instant.now();
        try {
            return new Policies<>(policies.values().stream()
                    .map(this::toObject)
                    .collect(Collectors.toList()));
        } finally {
            log.info("took {}ms to load all policies",
                    millisBetweenNowAnd(start));
        }
    }

    @Override
    public void delete(UUID id) {
        Instant start = Instant.now();
        try {
            policies.remove(id);
        } finally {
            log.info("took {}ms to delete policy with id {}",
                    millisBetweenNowAnd(start),
                    id);
        }
    }

    private T toObject(String json) {
        return converter.toObject(json, type);
    }

}
