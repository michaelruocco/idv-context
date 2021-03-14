package uk.co.idv.policy.usecases.policy.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.idv.policy.usecases.policy.PolicyRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO unit test
@RequiredArgsConstructor
@Slf4j
public class CachingRepositoryDecorator<T extends Policy> implements PolicyRepository<T>, CachingRepository {

    private final Map<UUID, T> cache = new ConcurrentHashMap<>();
    private final PolicyRepository<T> repository;
    private final CacheUpdateController cacheUpdateController;

    @Override
    public void refresh() {
        cacheUpdateController.startUpdate();
        log.info("cache size before refresh {}", cache.size());
        Map<UUID, T> newCache = loadNewCache();
        updateCache(newCache);
        log.info("cache size after refresh {}", cache.size());
        cacheUpdateController.completeUpdate();
    }

    @Override
    public void save(T policy) {
        log.debug("attempting to add policy to cache {}", policy.getId());
        cacheUpdateController.waitUntilUpdateComplete();
        cache.put(policy.getId(), policy);
        repository.save(policy);
        log.debug("policy added to cache {} and saved", policy.getId());
    }

    @Override
    public Optional<T> load(UUID id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Policies<T> loadAll() {
        log.info("loading all policies {} from cache", cache.size());
        cache.values().forEach(policy -> log.info(policy.toString()));
        return new Policies<>(cache.values());
    }

    @Override
    public void delete(UUID id) {
        log.debug("attempting to delete policy to cache {}", id);
        cacheUpdateController.waitUntilUpdateComplete();
        cache.remove(id);
        repository.delete(id);
        log.debug("policy removed {}", id);
    }

    private Map<UUID, T> loadNewCache() {
        return repository.loadAll().stream().collect(Collectors.toMap(Policy::getId, Function.identity()));
    }

    private void updateCache(Map<UUID, T> newCache) {
        newCache.forEach(cache::put);
        removeStaleCacheEntries(newCache);
    }

    private void removeStaleCacheEntries(Map<UUID, T> newCache) {
        cache.keySet().stream()
                .filter(key -> !newCache.containsKey(key))
                .forEach(cache::remove);
    }

}
