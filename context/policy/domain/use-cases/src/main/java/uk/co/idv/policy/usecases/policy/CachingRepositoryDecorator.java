package uk.co.idv.policy.usecases.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;

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
    //private boolean cacheInitialized = false;

    @Override
    public void refresh() {
        log.info("cache size before refresh {}", cache.size());
        Map<UUID, T> newCache = loadNewCache();
        if (!newCache.containsKey(UUID.fromString("98eb6d18-0d62-4883-a719-448286bc7a4b"))) {
            log.warn("***************************** new cache does not contain default abc policy id {}", newCache.keySet());
        }
        updateCache(newCache);
        log.info("cache size after refresh {}", cache.size());
        //cacheInitialized = true;
    }

    @Override
    public void save(T policy) {
        log.debug("adding policy to cache {}", policy);
        cache.put(policy.getId(), policy);
        repository.save(policy);
    }

    @Override
    public Optional<T> load(UUID id) {
        //initializeCacheIfRequired();
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Policies<T> loadAll() {
        //initializeCacheIfRequired();
        log.info("loading all policies {} from cache", cache.size());
        cache.values().forEach(policy -> log.info(policy.toString()));
        return new Policies<>(cache.values());
    }

    @Override
    public void delete(UUID id) {
        if (id.toString().equals("98eb6d18-0d62-4883-a719-448286bc7a4b")) {
            log.warn("***************************** DELETING abc default policy {}", id.toString());
        }
        cache.remove(id);
        repository.delete(id);
    }

    /*private void initializeCacheIfRequired() {
        if (cacheInitialized) {
            log.info("cache already initialized with {} values, not reinitializing", cache.size());
            return;
        }
        refresh();
        log.info("initialized cache with {} values", cache.size());
    }*/

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
                .peek(key -> log.warn("***************************** removing stale key from cache {}", key))
                .forEach(cache::remove);
    }

}
