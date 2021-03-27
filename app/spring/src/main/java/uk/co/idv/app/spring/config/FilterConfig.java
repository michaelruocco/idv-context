package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import uk.co.idv.app.spring.filters.logging.context.ContextRequestLoggingFilter;
import uk.co.idv.app.spring.filters.logging.context.ContextResponseLoggingFilter;
import uk.co.idv.app.spring.filters.logging.identity.IdentityRequestLoggingFilter;
import uk.co.idv.app.spring.filters.logging.identity.IdentityResponseLoggingFilter;
import uk.co.idv.app.spring.filters.logging.method.VerificationResponseLoggingFilter;
import uk.co.idv.app.spring.filters.validation.ContextHeaderValidationFilter;
import uk.co.idv.app.spring.filters.validation.DefaultHeaderValidationFilter;
import uk.co.mruoc.spring.filter.logging.mdc.ClearMdcFilter;
import uk.co.mruoc.spring.filter.logging.mdc.HeaderMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.mdc.RequestMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.uritransform.TransformRequestUriMdcPopulatorFilter;
import uk.co.mruoc.string.transform.UuidIdStringTransformer;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Configuration
@Slf4j
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
        bean.setEnabled(requestLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<DefaultHeaderValidationFilter> defaultHeaderValidationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        FilterRegistrationBean<DefaultHeaderValidationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new DefaultHeaderValidationFilter(handlerExceptionResolver));
        bean.setOrder(4);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.addUrlPatterns(getIdentityUrlPatterns());
        bean.setName("defaultHeaderValidationFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ContextHeaderValidationFilter> contextHeaderValidationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        FilterRegistrationBean<ContextHeaderValidationFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ContextHeaderValidationFilter(handlerExceptionResolver));
        bean.setOrder(4);
        bean.addUrlPatterns(getContextUrlPatterns());
        bean.setName("contextHeaderValidationFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> contextResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ContextResponseLoggingFilter(mapper));
        bean.setOrder(5);
        bean.addUrlPatterns(getContextUrlPatterns());
        bean.setName("contextResponseLoggingFilter");
        bean.setEnabled(responseLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> identityRequestLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IdentityRequestLoggingFilter(mapper));
        bean.setOrder(3);
        bean.addUrlPatterns(getIdentityUrlPatterns());
        bean.setName("identityRequestLoggingFilter");
        bean.setEnabled(requestLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> identityResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new IdentityResponseLoggingFilter(mapper));
        bean.setOrder(5);
        bean.addUrlPatterns(getIdentityUrlPatterns());
        bean.setName("identityResponseLoggingFilter");
        bean.setEnabled(responseLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> verificationResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new VerificationResponseLoggingFilter(mapper));
        bean.setOrder(3);
        bean.addUrlPatterns(getVerificationUrlPatterns());
        bean.setName("verificationResponseLoggingFilter");
        bean.setEnabled(responseLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> defaultRequestLoggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestLoggingFilter());
        bean.setOrder(3);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultRequestLoggingFilter");
        bean.setEnabled(requestLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> defaultResponseLoggingFilter() {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ResponseLoggingFilter());
        bean.setOrder(5);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultResponseLoggingFilter");
        bean.setEnabled(responseLoggingEnabled());
        return bean;
    }

    @Bean
    public FilterRegistrationBean<TransformRequestUriMdcPopulatorFilter> getContextUriTransformerFilter() {
        FilterRegistrationBean<TransformRequestUriMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new TransformRequestUriMdcPopulatorFilter(new UuidIdStringTransformer()));
        bean.setOrder(6);
        bean.addUrlPatterns(getContextUrlPatterns());
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
        patterns.addAll(Arrays.asList(getVerificationUrlPatterns()));
        patterns.addAll(Arrays.asList(getDefaultUrlPatterns()));
        return patterns.toArray(new String[0]);
    }

    //TODO set up logging masking for verifications endpoint responses

    private static String[] getContextUrlPatterns() {
        return new String[]{"/v1/contexts/*"};
    }

    private static String[] getIdentityUrlPatterns() {
        return new String[]{"/v1/identities/*"};
    }

    private static String[] getVerificationUrlPatterns() {
        return new String[]{"/v1/contexts/verifications/*"};
    }

    private static String[] getDefaultUrlPatterns() {
        return new String[]{
                "/v1/eligibility/*",
                "/v1/lockout-policies/*",
                "/v1/lockout-states/*",
                "/v1/context-policies/*"
        };
    }

    private static boolean requestLoggingEnabled() {
        return loadBooleanSystemProperty("request.logging.enabled");
    }

    private static boolean responseLoggingEnabled() {
        return loadBooleanSystemProperty("response.logging.enabled");
    }

    private static boolean loadBooleanSystemProperty(String key) {
        boolean enabled = Boolean.parseBoolean(System.getProperty(key, "false"));
        log.info("loaded {} value {}", key, enabled);
        return enabled;
    }

}
