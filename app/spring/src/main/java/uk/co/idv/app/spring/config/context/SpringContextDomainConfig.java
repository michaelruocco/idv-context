package uk.co.idv.app.spring.config.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.manual.config.AppConfig;
import uk.co.idv.context.config.ContextConfig;
import uk.co.idv.context.usecases.context.ContextFacade;
import uk.co.idv.context.usecases.policy.ContextPoliciesPopulator;
import uk.co.idv.context.usecases.policy.ContextPolicyService;

@Configuration
public class SpringContextDomainConfig {

    @Bean
    public ContextConfig contextConfig(AppConfig appConfig) {
        return appConfig.getContextConfig();
    }

    @Bean
    public ContextFacade contextFacade(ContextConfig contextConfig) {
        return contextConfig.getFacade();
    }

    @Bean
    public ContextPolicyService contextPolicyService(ContextConfig contextConfig) {
        return contextConfig.getPolicyService();
    }

    @Bean
    public ContextPoliciesPopulator contextPoliciesPopulator(ContextConfig contextConfig) {
        return contextConfig.getPoliciesPopulator();
    }

}
