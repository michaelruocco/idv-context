package uk.co.idv.app.spring.config.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.context.config.ContextServiceConfig;
import uk.co.idv.context.config.repository.ContextPolicyRepositoryConfig;
import uk.co.idv.context.config.repository.ContextRepositoryConfig;
import uk.co.idv.context.config.repository.DefaultParentContextRepositoryConfig;
import uk.co.idv.context.config.repository.ParentContextRepositoryConfig;
import uk.co.idv.context.usecases.policy.ContextPolicyService;

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
    public ContextServiceConfig contextServiceConfig(ParentContextRepositoryConfig repositoryConfig) {
        return ContextServiceConfig.builder()
                .repositoryConfig(repositoryConfig)
                .build();
    }

    @Bean
    public ContextPolicyService contextPolicyService(ContextServiceConfig config) {
        return config.policyService();
    }

}
