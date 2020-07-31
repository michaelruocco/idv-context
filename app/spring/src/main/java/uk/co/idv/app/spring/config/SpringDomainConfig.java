package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.config.identity.IdentityConfig;
import uk.co.idv.config.identity.repository.IdentityRepositoryConfig;
import uk.co.idv.context.adapter.eligibility.external.ExternalFindIdentityStubConfig;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.adapter.json.error.handler.IdentityErrorHandler;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.identity.IdentityFacade;

import java.time.Duration;
import java.util.concurrent.Executors;

@Configuration
public class SpringDomainConfig {

    @Bean
    public IdentityConfig identityConfig(IdentityRepositoryConfig repositoryConfig) {
        return IdentityConfig.builder()
                .repository(repositoryConfig.identityRepository())
                .stubConfig(stubConfig())
                .build();
    }

    @Bean
    public IdentityFacade identityFacade(IdentityConfig identityConfig) {
        return identityConfig.identityFacade();
    }

    @Bean
    public CreateEligibility createEligibility(IdentityConfig identityConfig) {
        return identityConfig.createEligibility();
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new IdentityErrorHandler();
    }

    private static ExternalFindIdentityStubConfig stubConfig() {
        return ExternalFindIdentityStubConfig.builder()
                .executor(Executors.newFixedThreadPool(2))
                .timeout(Duration.ofMillis(250))
                .phoneNumberDelay(Duration.ofMillis(400))
                .emailAddressDelay(Duration.ofMillis(100))
                .build();
    }

}
