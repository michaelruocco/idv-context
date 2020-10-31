package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.idv.app.manual.Application;
import uk.co.idv.app.manual.config.AppConfig;
import uk.co.idv.app.manual.adapter.app.AppAdapter;
import uk.co.idv.app.manual.adapter.app.DefaultAppAdapter;
import uk.co.idv.app.manual.adapter.channel.ChannelAdapter;
import uk.co.idv.app.manual.adapter.repository.RepositoryAdapter;
import uk.co.idv.common.adapter.json.error.handler.CommonApiErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.method.config.AppMethodConfig;
import uk.co.idv.method.usecases.MethodBuilders;

import java.time.Clock;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SpringCommonDomainConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public ErrorHandler commonErrorHandler() {
        return new CommonApiErrorHandler();
    }

    @Bean
    @Primary
    public ErrorHandler errorHandler(Collection<ErrorHandler> errorHandlers) {
        return new CompositeErrorHandler(errorHandlers);
    }

    @Bean
    public AppAdapter appAdapter(RepositoryAdapter repositoryAdapter) {
        return DefaultAppAdapter.builder()
                .repositoryAdapter(repositoryAdapter)
                .build();
    }

    @Bean
    public MethodBuilders methodBuilders(Collection<AppMethodConfig> methodConfigs) {
        return new MethodBuilders(methodConfigs.stream()
                .map(AppMethodConfig::methodBuilder)
                .collect(Collectors.toList())
        );
    }

    @Bean
    public AppConfig appConfig(MethodBuilders methodBuilders,
                               RepositoryAdapter repositoryAdapter,
                               AppAdapter appAdapter) {
        return new AppConfig(methodBuilders, repositoryAdapter, appAdapter);
    }

    @Bean
    public Application application(AppConfig appConfig) {
        return new Application(appConfig);
    }

    @Bean
    public StartupListener startupListener(Application application,
                                           ChannelAdapter channelAdapter) {
        return StartupListener.builder()
                .application(application)
                .channelAdapter(channelAdapter)
                .build();
    }

}
