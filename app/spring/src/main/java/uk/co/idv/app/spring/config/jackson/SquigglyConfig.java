package uk.co.idv.app.spring.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.app.plain.config.JsonConfig;

@Configuration
@ConditionalOnProperty(value = "response.filtering.enabled", havingValue = "true")
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter() {
        FilterRegistrationBean<SquigglyRequestFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new SquigglyRequestFilter());
        filter.setOrder(1);
        return filter;
    }

    @Bean
    public ObjectMapper squigglyObjectMapper(JsonConfig jsonConfig) {
        ObjectMapper mapper = jsonConfig.getObjectMapper();
        Squiggly.init(mapper, new RequestSquigglyContextProvider());
        return mapper;
    }

}
