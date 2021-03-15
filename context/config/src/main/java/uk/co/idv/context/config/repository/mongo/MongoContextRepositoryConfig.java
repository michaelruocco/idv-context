package uk.co.idv.context.config.repository.mongo;

import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.adapter.repository.MongoContextRepository;
import uk.co.idv.context.adapter.repository.policy.CachingMongoContextPolicyRepository;
import uk.co.idv.context.adapter.repository.policy.MongoContextPolicyRepository;
import uk.co.idv.context.config.repository.ContextPolicyRepositoryConfig;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

@RequiredArgsConstructor
public class MongoContextRepositoryConfig implements ContextRepositoryConfig, ContextPolicyRepositoryConfig {

    private final ContextRepository contextRepository;
    private final CachingMongoContextPolicyRepository policyRepository;

    public MongoContextRepositoryConfig(JsonConverter jsonConverter,
                                        MongoDatabase database) {
        this(toContextRepository(database, jsonConverter),
                toPolicyRepository(database, jsonConverter));
    }

    @Override
    public ContextRepository contextRepository() {
        return contextRepository;
    }

    @Override
    public ContextPolicyRepository policyRepository() {
        return policyRepository;
    }

    public String getChangeLogPackageName() {
        return MongoContextChangeLog.class.getPackageName();
    }

    public Runnable getPolicyRefreshTask() {
        return policyRepository;
    }

    private static ContextRepository toContextRepository(MongoDatabase database, JsonConverter jsonConverter) {
        return new MongoContextRepository(database, jsonConverter);
    }

    private static CachingMongoContextPolicyRepository toPolicyRepository(MongoDatabase database, JsonConverter jsonConverter) {
        return new CachingMongoContextPolicyRepository(new MongoContextPolicyRepository(database, jsonConverter));
    }

}
