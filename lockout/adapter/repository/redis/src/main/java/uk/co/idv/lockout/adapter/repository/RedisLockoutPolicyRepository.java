package uk.co.idv.lockout.adapter.repository;

import lombok.Builder;
import uk.co.idv.context.adapter.repository.RedisPolicyRepository;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Map;
import java.util.UUID;

public class RedisLockoutPolicyRepository
        extends RedisPolicyRepository<LockoutPolicy>
        implements LockoutPolicyRepository {

    @Builder
    public RedisLockoutPolicyRepository(JsonConverter converter, Map<UUID, String> policies) {
        super(LockoutPolicy.class, converter, policies);
    }

}
