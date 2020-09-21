package uk.co.idv.context.config.repository.redis;

import lombok.Builder;
import uk.co.idv.context.adapter.repository.RedisContextPolicyRepository;
import uk.co.idv.context.config.repository.ContextPolicyRepositoryConfig;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Map;
import java.util.UUID;

@Builder
public class RedisContextPolicyRepositoryConfig implements ContextPolicyRepositoryConfig {

    private final JsonConverter jsonConverter;
    private final Map<UUID, String> policies;

    @Override
    public ContextPolicyRepository policyRepository() {
        return RedisContextPolicyRepository.builder()
                .converter(jsonConverter)
                .policies(policies)
                .build();
    }

}
