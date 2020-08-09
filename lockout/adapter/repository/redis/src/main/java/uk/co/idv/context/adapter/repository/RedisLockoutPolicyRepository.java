package uk.co.idv.context.adapter.repository;

import lombok.Builder;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.usecases.lockout.LockoutPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Map;
import java.util.UUID;

public class RedisLockoutPolicyRepository
        extends RedisPolicyRespository<LockoutPolicy>
        implements LockoutPolicyRepository {

    @Builder
    public RedisLockoutPolicyRepository(JsonConverter converter, Map<UUID, String> policies) {
        super(LockoutPolicy.class, converter, policies);
    }

}
