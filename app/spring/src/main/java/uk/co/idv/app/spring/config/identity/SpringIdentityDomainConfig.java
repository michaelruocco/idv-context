package uk.co.idv.app.spring.config.identity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.manual.AppConfig;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;


@Configuration
public class SpringIdentityDomainConfig {

    @Bean
    public IdentityConfig identityFacade(AppConfig appConfig) {
        return appConfig.getIdentityConfig();
    }

    @Bean
    public IdentityService identityService(IdentityConfig identityConfig) {
        return identityConfig.identityService();
    }

    @Bean
    public CreateEligibility createEligibility(IdentityConfig identityConfig) {
        return identityConfig.createEligibility();
    }

    @Bean
    public ErrorHandler identityErrorHandler(IdentityConfig identityConfig) {
        return identityConfig.errorHandler();
    }

}
