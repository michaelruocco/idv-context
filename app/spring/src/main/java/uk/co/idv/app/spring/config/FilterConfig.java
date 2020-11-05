package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import uk.co.idv.app.spring.filters.logging.context.ContextRequestLoggingFilter;
import uk.co.idv.app.spring.filters.logging.context.ContextResponseLoggingFilter;
import uk.co.idv.app.spring.filters.logging.identity.IdentityRequestLoggingFilter;
import uk.co.idv.app.spring.filters.logging.identity.IdentityResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.mdc.ClearMdcFilter;
import uk.co.mruoc.spring.filter.logging.mdc.HeaderMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.mdc.RequestMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.uritransform.TransformRequestUriMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.uritransform.UuidIdStringTransformer;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HeaderMdcPopulatorFilter> headerMdcPopulator() {
        FilterRegistrationBean<HeaderMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HeaderMdcPopulatorFilter("correlation-id", "channel-id"));
        bean.setOrder(1);
        bean.addUrlPatterns(allUrlPatterns());
        bean.setName("headerMdcPopulator");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestMdcPopulatorFilter> requestMdcPopulator(Clock clock) {
        FilterRegistrationBean<RequestMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestMdcPopulatorFilter(clock));
        bean.setOrder(2);
        bean.addUrlPatterns(allUrlPatterns());
        bean.setName("requestMdcPopulator");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> contextRequestLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ContextRequestLoggingFilter(mapper));
        bean.setOrder(3);
        bean.addUrlPatterns(getContextUrlPatterns());
        bean.setName("contextRequestLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> contextResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ContextResponseLoggingFilter(mapper));
        bean.setOrder(4);
        bean.addUrlPatterns(getContextUrlPatterns());
        bean.setName("contextResponseLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> identityRequestLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IdentityRequestLoggingFilter(mapper));
        bean.setOrder(3);
        bean.addUrlPatterns(getIdentityUrlPatterns());
        bean.setName("identityRequestLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> identityResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IdentityResponseLoggingFilter(mapper));
        bean.setOrder(4);
        bean.addUrlPatterns(getIdentityUrlPatterns());
        bean.setName("identityResponseLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> defaultRequestLoggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestLoggingFilter());
        bean.setOrder(3);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultRequestLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> defaultResponseLoggingFilter() {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ResponseLoggingFilter());
        bean.setOrder(4);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultResponseLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<TransformRequestUriMdcPopulatorFilter> getContextUriTransformerFilter() {
        FilterRegistrationBean<TransformRequestUriMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TransformRequestUriMdcPopulatorFilter(new UuidIdStringTransformer()));
        bean.setOrder(5);
        bean.addUrlPatterns("/contexts/*");
        bean.setName("getContextUriTransformerFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ClearMdcFilter> clearMdcFilter() {
        FilterRegistrationBean<ClearMdcFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ClearMdcFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns(allUrlPatterns());
        bean.setName("clearMdcFilter");
        return bean;
    }

    private static String[] allUrlPatterns() {
        Collection<String> patterns = new ArrayList<>();
        patterns.addAll(Arrays.asList(getContextUrlPatterns()));
        patterns.addAll(Arrays.asList(getIdentityUrlPatterns()));
        patterns.addAll(Arrays.asList(getDefaultUrlPatterns()));
        return patterns.toArray(new String[0]);
    }

    private static String[] getContextUrlPatterns() {
        return new String[]{
                "/contexts/*"
        };
    }

    private static String[] getIdentityUrlPatterns() {
        return new String[]{"/identities/*"};
    }

    private static String[] getDefaultUrlPatterns() {
        return new String[]{
                "/eligibility/*",
                "/lockout-policies/*",
                "/lockout-states/*",
                "/context-policies/*"
        };
    }

}
