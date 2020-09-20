package uk.co.idv.app.spring.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.config.repository.inmemory.InMemoryContextRepositoryConfig;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.config.repository.inmemory.InMemoryIdentityRepositoryConfig;
import uk.co.idv.lockout.config.repository.LockoutRepositoryConfig;
import uk.co.idv.lockout.config.repository.inmemory.InMemoryLockoutRepositoryConfig;

@Configuration
@Profile("stubbed")
public class SpringStubbedRepositoryConfig {

    @Bean
    public IdentityRepositoryConfig identityRepositoryConfig() {
        return new InMemoryIdentityRepositoryConfig();
    }

    @Bean
    public LockoutRepositoryConfig lockoutRepositoryConfig() {
        return new InMemoryLockoutRepositoryConfig();
    }

    @Bean
    public ContextRepositoryConfig contextRepositoryConfig() {
        return new InMemoryContextRepositoryConfig();
    }

}
