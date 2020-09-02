package uk.co.idv.app.spring.config.lockout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.identity.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.context.config.lockout.LockoutConfig;
import uk.co.idv.context.config.lockout.repository.AttemptRepositoryConfig;
import uk.co.idv.context.config.lockout.repository.LockoutPolicyRepositoryConfig;
import uk.co.idv.context.usecases.lockout.LockoutFacade;
import uk.co.idv.context.usecases.lockout.policy.LockoutPolicyService;

@Configuration
public class SpringLockoutDomainConfig {

    @Bean
    public LockoutConfig lockoutConfig(LockoutPolicyRepositoryConfig policyRepositoryConfig,
                                       AttemptRepositoryConfig attemptRepositoryConfig,
                                       IdentityConfig identityConfig) {
        return LockoutConfig.builder()
                .policyRepository(policyRepositoryConfig.policyRepository())
                .attemptRepository(attemptRepositoryConfig.attemptRepository())
                .aliasFactory(identityConfig.aliasFactory())
                .findIdentityProvider(identityConfig)
                .build();
    }

    @Bean
    public LockoutFacade lockoutFacade(LockoutConfig lockoutConfig) {
        return lockoutConfig.lockoutFacade();
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
