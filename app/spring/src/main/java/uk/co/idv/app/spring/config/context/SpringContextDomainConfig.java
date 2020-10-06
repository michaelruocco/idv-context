package uk.co.idv.app.spring.config.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.config.ContextFacadeConfig;
import uk.co.idv.context.config.ContextServiceConfig;
import uk.co.idv.context.config.repository.ContextPolicyRepositoryConfig;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.config.repository.DefaultParentContextRepositoryConfig;
import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPolicyService;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.lockout.config.LockoutConfig;
import uk.co.idv.method.usecases.MethodBuilder;

import java.util.Collection;

@Configuration
public class SpringContextDomainConfig {

    @Bean
    public ParentContextRepositoryConfig parentRepositoryConfig(ContextRepositoryConfig contextRepositoryConfig,
                                                                ContextPolicyRepositoryConfig policyRepositoryConfig) {
        return DefaultParentContextRepositoryConfig.builder()
                .contextRepositoryConfig(contextRepositoryConfig)
                .policyRepositoryConfig(policyRepositoryConfig)
                .build();
    }

    @Bean
    public ContextServiceConfig contextServiceConfig(ParentContextRepositoryConfig repositoryConfig,
                                                     Collection<MethodBuilder> methodBuilders) {
        return ContextServiceConfig.builder()
                .repositoryConfig(repositoryConfig)
                .methodBuilders(methodBuilders)
                .build();
    }

    @Bean
    public ContextFacadeConfig contextFacadeConfig(ContextServiceConfig serviceConfig,
                                                   LockoutConfig lockoutConfig,
                                                   IdentityConfig identityConfig) {
        return ContextFacadeConfig.builder()
                .serviceConfig(serviceConfig)
                .lockoutService(lockoutConfig.lockoutService())
                .createEligibility(identityConfig.createEligibility())
                .build();
    }

    @Bean
    public ContextPolicyService contextPolicyService(ContextServiceConfig config) {
        return config.policyService();
    }

    @Bean
    public ContextFacade contextFacade(ContextFacadeConfig config) {
        return config.contextFacade();
    }

}
