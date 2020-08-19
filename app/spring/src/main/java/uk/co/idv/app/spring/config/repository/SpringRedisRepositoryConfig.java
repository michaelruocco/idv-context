
package uk.co.idv.app.spring.config.repository;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.config.lockout.repository.LockoutPolicyRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.redis.RedisLockoutPolicyRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.redis.RedissonMapFactory;
import uk.co.mruoc.json.JsonConverter;

import static uk.co.idv.app.spring.config.repository.EnvironmentLoader.loadEnvironment;

@Configuration
@Profile("!stubbed")
public class SpringRedisRepositoryConfig {

    @Bean
    public LockoutPolicyRepositoryConfig lockoutPolicyRepositoryConfig(JsonConverter jsonConverter) {
        RedissonMapFactory factory = RedissonMapFactory.builder()
                .client(redissionClient())
                .environment(loadEnvironment())
                .build();
        return RedisLockoutPolicyRepositoryConfig.builder()
                .policies(factory.buildLockoutPolicyMap())
                .jsonConverter(jsonConverter)
                .build();
    }

    private static RedissonClient redissionClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(System.getProperty("redis.endpoint.uri", "redis://redis:6379"));
        return Redisson.create(config);
    }

}
