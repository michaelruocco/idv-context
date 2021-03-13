package uk.co.idv.app.spring.config.repository;

import com.github.mongobee.Mongobee;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.config.repository.mongo.MongoContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextRepository;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.identity.config.repository.mongo.MongoIdentityRepositoryConfig;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.lockout.config.repository.mongo.MongoLockoutRepositoryConfig;
import uk.co.idv.lockout.usecases.attempt.AttemptRepository;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import java.util.Optional;
import java.util.concurrent.Executors;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@Profile("!stubbed")
public class SpringMongoRepositoryConfig {

    private final ConnectionString connectionString = loadConnectionString();

    @Bean
    public MongoClient mongoClient() {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient client) {
        return client.getDatabase(loadDatabaseName());
    }

    @Bean
    public MongoContextRepositoryConfig mongoContextRepositoryConfig(JsonConverter jsonConverter, MongoDatabase database) {
        return new MongoContextRepositoryConfig(jsonConverter, database);
    }

    @Bean
    public Mongobee contextMongobee(MongoContextRepositoryConfig config) {
        return toMongobee(config.getChangeLogPackageName());
    }

    @Bean
    public ContextRepository contextRepository(MongoContextRepositoryConfig config) {
        return config.contextRepository();
    }

    @Profile("!redis")
    @Bean
    public ContextPolicyRepository contextPolicyRepository(MongoContextRepositoryConfig config) {
        return config.policyRepository();
    }

    @Bean
    public MongoIdentityRepositoryConfig identityRepositoryConfig(MongoDatabase database, AliasFactory aliasFactory) {
        return new MongoIdentityRepositoryConfig(database, aliasFactory);
    }

    @Bean
    public Mongobee identityMongobee(MongoIdentityRepositoryConfig config) {
        return toMongobee(config.getChangeLogPackageName());
    }

    @Bean
    public IdentityRepository identityRepository(MongoIdentityRepositoryConfig config) {
        return config.identityRepository();
    }

    @Bean
    public MongoLockoutRepositoryConfig mongoLockoutRepositoryConfig(JsonConverter jsonConverter, MongoDatabase database) {
        return new MongoLockoutRepositoryConfig(jsonConverter, database);
    }

    @Bean
    public Mongobee lockoutMongobee(MongoLockoutRepositoryConfig config) {
        return toMongobee(config.getChangeLogPackageName());
    }

    @Bean
    public AttemptRepository attemptRepository(MongoLockoutRepositoryConfig config) {
        return config.attemptRepository();
    }

    @Profile("!redis")
    @Bean
    public LockoutPolicyRepository lockoutPolicyRepository(MongoLockoutRepositoryConfig config) {
        return config.policyRepository();
    }

    @Bean
    public SchedulePolicyRefresh schedulePolicyRefresh(MongoContextRepositoryConfig contextConfig,
                                                       MongoLockoutRepositoryConfig lockoutConfig) {
        return SchedulePolicyRefresh.builder()
                .scheduledExecutor(Executors.newScheduledThreadPool(loadPolicyRefreshThreadPoolSize()))
                .policyRefreshDelay(loadPolicyRefreshDelay())
                .contextPolicyRefreshTask(contextConfig.getPolicyRefreshTask())
                .lockoutPolicyRefreshTask(lockoutConfig.getPolicyRefreshTask())
                .build();
    }

    private Mongobee toMongobee(String changeLogPackageName) {
        Mongobee runner = new Mongobee(connectionString.getConnectionString());
        runner.setChangeLogsScanPackage(changeLogPackageName);
        return runner;
    }

    private String loadDatabaseName() {
        return Optional.ofNullable(connectionString.getDatabase())
                .orElseThrow(() -> new IllegalStateException(toErrorMessage(connectionString)));
    }

    private static String toErrorMessage(ConnectionString connectionString) {
        return String.format("mongo connection string must contain database name %s",
                connectionString.getConnectionString());
    }

    private static int loadPolicyRefreshThreadPoolSize() {
        return Integer.parseInt(System.getProperty("policy.refresh.thread.pool.size", "4"));
    }

    private static int loadPolicyRefreshDelay() {
        return Integer.parseInt(System.getProperty("policy.refresh.delay", "60000"));
    }

    private static ConnectionString loadConnectionString() {
        return new ConnectionString(System.getProperty("spring.data.mongodb.uri"));
    }

}
