package uk.co.idv.policy.usecases.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
public class CachingRepositoryDecorator<T extends Policy> implements PolicyRepository<T>, CachingRepository {

    private final Map<UUID, T> cache = new ConcurrentHashMap<>();
    private final PolicyRepository<T> repository;
    private boolean cacheInitialized = false;

    @Override
    public void refresh() {
        log.info("cache size before refresh {}", cache.size());
        Map<UUID, T> newCache = loadNewCache();
        updateCache(newCache);
        log.info("cache size after refresh {}", cache.size());
        cacheInitialized = true;
    }

    @Override
    public void save(T policy) {
        log.debug("adding policy to cache {}", policy);
        cache.put(policy.getId(), policy);
        repository.save(policy);
    }

    @Override
    public Optional<T> load(UUID id) {
        initializeCacheIfRequired();
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Policies<T> loadAll() {
        log.debug("cache values before initialize {}", cache.values());
        initializeCacheIfRequired();
        log.info("loaded all {} values from cache", cache.size());
        log.debug("cache values after initialize {}", cache.values());
        return new Policies<>(cache.values());
    }

    @Override
    public void delete(UUID id) {
        cache.remove(id);
        repository.delete(id);
    }

    private void initializeCacheIfRequired() {
        if (!cacheInitialized) {
            refresh();
            log.info("initializing cache with {} values", cache.size());
        }
    }

    private Map<UUID, T> loadNewCache() {
        Map<UUID, T> newCache = new HashMap<>();
        repository.loadAll().stream().forEach(policy -> newCache.put(policy.getId(), policy));
        return newCache;
    }

    private void updateCache(Map<UUID, T> newCache) {
        newCache.forEach(cache::put);
        removeStaleCacheEntries(newCache);
    }

    private void removeStaleCacheEntries(Map<UUID, T> newCache) {
        cache.keySet().stream().filter(key -> !newCache.containsKey(key)).forEach(cache::remove);
    }

}
