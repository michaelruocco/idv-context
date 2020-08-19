package uk.co.idv.app.spring.config.repository;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.dynamo.DynamoTableFactory;
import uk.co.idv.context.adapter.dynamo.DynamoTables;
import uk.co.idv.context.config.identity.respository.IdentityRepositoryConfig;
import uk.co.idv.context.config.identity.respository.dynamo.DynamoIdentityRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.LockoutPolicyRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.redis.RedisLockoutPolicyRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.redis.RedissonMapFactory;
import uk.co.mruoc.json.JsonConverter;

@Configuration
@Profile("!stubbed")
public class SpringDefaultRepositoryConfig {

    @Bean
    public IdentityRepositoryConfig identityRepositoryConfig(JsonConverter jsonConverter) {
        DynamoTableFactory tableFactory = DynamoTableFactory.builder()
                .environment(System.getProperty("environment", "idv-local"))
                .region(System.getProperty("aws.region", "eu-west-1"))
                .endpointUrl(System.getProperty("aws.dynamo.db.endpoint.uri", "http://localhost:4569"))
                .environment(loadEnvironment())
                .build();
        DynamoTables tables = tableFactory.build();
        return new DynamoIdentityRepositoryConfig(jsonConverter, tables);
    }

    @Bean
    public LockoutPolicyRepositoryConfig lockoutRepositoryConfig(JsonConverter jsonConverter) {
        RedissonMapFactory factory = RedissonMapFactory.builder()
                .client(redissionClient())
                .environment(loadEnvironment())
                .build();
        return RedisLockoutPolicyRepositoryConfig.builder()
                .policies(factory.buildLockoutPolicyMap())
                .jsonConverter(jsonConverter)
                .build();
    }

    @Bean
    public RedissonClient redissionClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(System.getProperty("redis.endpoint.uri", "redis://redis:6379"));
        return Redisson.create(config);
    }

    private static String loadEnvironment() {
        return System.getProperty("environment", "idv-local");
    }

}
