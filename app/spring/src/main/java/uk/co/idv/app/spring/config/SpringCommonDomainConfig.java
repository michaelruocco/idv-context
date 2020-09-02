package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.idv.identity.adapter.json.error.handler.CommonApiErrorHandler;
import uk.co.idv.identity.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.identity.adapter.json.error.handler.ErrorHandler;

import java.util.Collection;

@Configuration
public class SpringCommonDomainConfig {

    @Bean
    public ErrorHandler commonErrorHandler() {
        return new CommonApiErrorHandler();
    }

    @Bean
    @Primary
    public ErrorHandler errorHandler(Collection<ErrorHandler> errorHandlers) {
        return new CompositeErrorHandler(errorHandlers);
    }

}
