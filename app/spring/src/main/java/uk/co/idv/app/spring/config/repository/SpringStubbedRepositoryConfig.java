package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.config.repository.inmemory.InMemoryIdentityRepositoryConfig;
import uk.co.idv.lockout.config.repository.LockoutPolicyRepositoryConfig;
import uk.co.idv.lockout.config.repository.inmemory.InMemoryLockoutRepositoryConfig;

@Configuration
@Profile("stubbed")
public class SpringStubbedRepositoryConfig {

    @Bean
    @Profile("stubbed")
    public IdentityRepositoryConfig identityRepositoryConfig() {
        return new InMemoryIdentityRepositoryConfig();
    }

    @Bean
    @Profile("stubbed")
    public LockoutPolicyRepositoryConfig lockoutRepositoryConfig() {
        return new InMemoryLockoutRepositoryConfig();
    }

}
