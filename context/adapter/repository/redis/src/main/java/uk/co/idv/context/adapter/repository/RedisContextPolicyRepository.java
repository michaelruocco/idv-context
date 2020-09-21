package uk.co.idv.context.adapter.repository;

import lombok.Builder;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Map;
import java.util.UUID;

public class RedisContextPolicyRepository
        extends RedisPolicyRespository<ContextPolicy>
        implements ContextPolicyRepository {

    @Builder
    public RedisContextPolicyRepository(JsonConverter converter, Map<UUID, String> policies) {
        super(ContextPolicy.class, converter, policies);
    }

}
