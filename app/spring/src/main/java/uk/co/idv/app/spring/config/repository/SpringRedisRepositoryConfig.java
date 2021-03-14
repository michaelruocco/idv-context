
package uk.co.idv.app.spring.config.repository;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.adapter.repository.RedissonMapFactory;
import uk.co.idv.context.config.repository.redis.RedisContextPolicyRepositoryConfig;
import uk.co.idv.context.usecases.policy.ContextPolicyRepository;
import uk.co.idv.lockout.config.repository.redis.RedisLockoutPolicyRepositoryConfig;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyRepository;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.app.spring.config.repository.EnvironmentLoader.loadEnvironment;

@Configuration
@Profile("redis")
@Slf4j
public class SpringRedisRepositoryConfig {

    @Bean
    public LockoutPolicyRepository lockoutPolicyRepository(RedissonMapFactory mapFactory,
                                                           JsonConverter jsonConverter) {
        return RedisLockoutPolicyRepositoryConfig.builder()
                .policies(mapFactory.buildPolicyMap("lockout-policy"))
                .jsonConverter(jsonConverter)
                .build()
                .policyRepository();
    }

    @Bean
    public ContextPolicyRepository contextPolicyRepository(RedissonMapFactory mapFactory,
                                                           JsonConverter jsonConverter) {
        return RedisContextPolicyRepositoryConfig.builder()
                .policies(mapFactory.buildPolicyMap("context-policy"))
                .jsonConverter(jsonConverter)
                .build()
                .policyRepository();
    }

    @Bean
    public RedissonMapFactory mapFactory() {
        return RedissonMapFactory.builder()
                .client(redissionClient())
                .environment(loadEnvironment())
                .build();
    }

    private static RedissonClient redissionClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(System.getProperty("redis.endpoint.uri", "redis://redis:6379"));
        log.info("creating redisson client with address {}", config.useSingleServer().getAddress());
        return Redisson.create(config);
    }

}
