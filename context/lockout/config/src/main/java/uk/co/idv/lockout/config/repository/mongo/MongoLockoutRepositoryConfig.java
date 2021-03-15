package uk.co.idv.lockout.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import uk.co.idv.lockout.adapter.repository.MongoAttemptRepository;
import uk.co.idv.lockout.adapter.repository.policy.CachingMongoLockoutPolicyRepository;
import uk.co.idv.lockout.adapter.repository.policy.MongoLockoutPolicyRepository;
import uk.co.idv.lockout.config.repository.AttemptRepositoryConfig;
import uk.co.idv.lockout.config.repository.LockoutPolicyRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

@RequiredArgsConstructor
public class MongoLockoutRepositoryConfig implements AttemptRepositoryConfig, LockoutPolicyRepositoryConfig {

    private final AttemptRepository attemptRepository;
    private final CachingMongoLockoutPolicyRepository policyRepository;

    public MongoLockoutRepositoryConfig(JsonConverter jsonConverter,
                                        MongoDatabase database) {
        this(toAttemptRepository(database, jsonConverter),
                toPolicyRepository(database, jsonConverter));
    }

    @Override
    public AttemptRepository attemptRepository() {
        return attemptRepository;
    }

    @Override
    public LockoutPolicyRepository policyRepository() {
        return policyRepository;
    }

    public String getChangeLogPackageName() {
        return MongoLockoutChangeLog.class.getPackageName();
    }

    public Runnable getPolicyRefreshTask() {
        return policyRepository;
    }

    private static AttemptRepository toAttemptRepository(MongoDatabase database, JsonConverter jsonConverter) {
        return new MongoAttemptRepository(database, jsonConverter);
    }

    private static CachingMongoLockoutPolicyRepository toPolicyRepository(MongoDatabase database, JsonConverter jsonConverter) {
        return new CachingMongoLockoutPolicyRepository(new MongoLockoutPolicyRepository(database, jsonConverter));
    }

}
