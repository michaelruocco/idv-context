package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import uk.co.idv.app.plain.Application;
import uk.co.idv.app.plain.config.AppConfig;
import uk.co.idv.app.plain.adapter.app.AppAdapter;
import uk.co.idv.app.plain.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.plain.adapter.repository.RepositoryAdapter;
import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.context.ContextConverter;
import uk.co.idv.method.config.AppMethodConfigs;
import uk.co.idv.identity.adapter.eligibility.external.StubExternalFindIdentityConfig;
import uk.co.idv.identity.config.ExternalFindIdentityConfig;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.method.config.AppMethodConfig;

import java.time.Clock;
import java.util.Collection;

@Configuration
public class SpringCommonDomainConfig {

    @Profile("!test")
    @Bean
    public ExternalFindIdentityConfig defaultFindIdentityConfig() {
        return StubExternalFindIdentityConfig.build();
    }

    @Profile("test")
    @Bean
    public ExternalFindIdentityConfig testFindIdentityConfig() {
        return StubExternalFindIdentityConfig.withNoDelays();
    }

    @Bean
    public AliasFactory aliasFactory(AppAdapter appAdapter) {
        return appAdapter.getAliasFactory();
    }

    @Bean
    public ErrorHandler commonErrorHandler(AppAdapter appAdapter) {
        return appAdapter.getErrorHandler();
    }

    @Bean
    @Primary
    public ErrorHandler errorHandler(Collection<ErrorHandler> errorHandlers) {
        return new CompositeErrorHandler(errorHandlers);
    }

    @Bean
    public AppAdapter appAdapter(Clock clock) {
        return DefaultAppAdapter.builder()
                .clock(clock)
                .build();
    }

    @Bean
    public AppMethodConfigs methodConfigs(Collection<AppMethodConfig> methodConfigs) {
        return new AppMethodConfigs(methodConfigs);
    }

    @Bean
    public AppConfig appConfig(AppMethodConfigs appMethodConfigs,
                               RepositoryAdapter repositoryAdapter,
                               AppAdapter appAdapter,
                               ExternalFindIdentityConfig externalFindIdentityConfig) {
        return new AppConfig(appMethodConfigs, repositoryAdapter, appAdapter, externalFindIdentityConfig);
    }

    @Bean
    public Application application(AppConfig appConfig) {
        return new Application(appConfig);
    }

    @Bean
    public ContextConverter contextConverter() {
        return new ContextConverter();
    }

}
