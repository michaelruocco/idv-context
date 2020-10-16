package uk.co.idv.context.adapter.repository;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RedissonClient;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Builder
public class RedissonMapFactory {

    private final RedissonClient client;
    private final String environment;

    public Map<UUID, String> buildPolicyMap(String name) {
        return client.getLocalCachedMap(prefixEnvironment(name), LocalCachedMapOptions.defaults());
    }

    private String prefixEnvironment(String name) {
        return String.format("%s-%s", environment, name);
    }

}
