package uk.co.idv.app.spring.config.identity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.identity.adapter.eligibility.external.ExternalFindIdentityStubConfig;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.config.IdentityConfig;
import uk.co.idv.identity.config.repository.IdentityRepositoryConfig;
import uk.co.idv.identity.usecases.eligibility.CreateEligibility;
import uk.co.idv.identity.usecases.identity.IdentityService;

import java.time.Duration;
import java.util.concurrent.Executors;

@Configuration
public class SpringIdentityDomainConfig {

    @Bean
    public IdentityConfig identityConfig(IdentityRepositoryConfig repositoryConfig) {
        return IdentityConfig.builder()
                .repository(repositoryConfig.identityRepository())
                .stubConfig(stubConfig())
                .build();
    }

    @Bean
    public IdentityService identityFacade(IdentityConfig identityConfig) {
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

    private static ExternalFindIdentityStubConfig stubConfig() {
        return ExternalFindIdentityStubConfig.builder()
                .executor(Executors.newFixedThreadPool(2))
                .timeout(Duration.ofMillis(250))
                .phoneNumberDelay(Duration.ofMillis(400))
                .emailAddressDelay(Duration.ofMillis(100))
                .build();
    }

}
