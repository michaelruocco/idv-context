package uk.co.idv.context.config.lockout.repository.redis;

import lombok.Builder;
import uk.co.idv.context.adapter.repository.RedisLockoutPolicyRepository;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.usecases.lockout.LockoutPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Map;
import java.util.UUID;

@Builder
public class RedisLockoutRepositoryConfig implements LockoutRepositoryConfig {

    private final JsonConverter jsonConverter;
    private final Map<UUID, String> policies;

    @Override
    public LockoutPolicyRepository policyRepository() {
        return RedisLockoutPolicyRepository.builder()
                .converter(jsonConverter)
                .policies(policies)
                .build();
    }

}
