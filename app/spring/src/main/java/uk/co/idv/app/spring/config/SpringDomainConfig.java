package uk.co.idv.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.idv.context.adapter.json.error.handler.CompositeErrorHandler;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerHandler;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

import java.util.Collection;

@Configuration
public class SpringDomainConfig {

    @Bean
    @Primary
    public ErrorHandler errorHandler(Collection<ErrorHandler> errorHandlers) {
        return new CompositeErrorHandler(
                new InternalServerHandler(),
                errorHandlers
        );
    }

}
