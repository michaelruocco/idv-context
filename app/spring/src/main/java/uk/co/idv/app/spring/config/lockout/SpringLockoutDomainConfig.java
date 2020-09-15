package uk.co.idv.app.spring.config.lockout;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.lockout.config.repository.AttemptRepositoryConfig;
import uk.co.idv.lockout.config.repository.LockoutPolicyRepositoryConfig;
import uk.co.idv.lockout.usecases.LockoutFacade;
import uk.co.idv.lockout.usecases.policy.LockoutPolicyService;

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
                .findIdentity(identityConfig.findIdentity())
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
