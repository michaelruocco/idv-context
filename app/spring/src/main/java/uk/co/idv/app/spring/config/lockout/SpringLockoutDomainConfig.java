package uk.co.idv.app.spring.config.lockout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.config.lockout.repository.LockoutRepositoryConfig;
import uk.co.idv.context.usecases.lockout.LockoutPolicyService;

@Configuration
public class SpringLockoutDomainConfig {

    @Bean
    public LockoutConfig lockoutConfig(LockoutRepositoryConfig repositoryConfig) {
        return LockoutConfig.builder()
                .repository(repositoryConfig.policyRepository())
                .build();
    }

    @Bean
    public LockoutPolicyService lockoutPolicyService(LockoutConfig lockoutConfig) {
        return lockoutConfig.policyService();
    }

    @Bean
    public ErrorHandler lockoutErrorHandler(LockoutConfig lockoutConfig) {
        return lockoutConfig.errorHandler();
    }

}
