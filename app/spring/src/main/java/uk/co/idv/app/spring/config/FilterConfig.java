package uk.co.idv.app.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathConfig;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.json.mask.MaskFunction;
import uk.co.mruoc.json.mask.string.IgnoreFirstNAndLastNCharsMasker;
import uk.co.mruoc.json.mask.string.IgnoreLastNCharsMasker;
import uk.co.mruoc.spring.filter.logging.RequestMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.header.HeaderMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.request.TransformingRequestBodyExtractor;
import uk.co.mruoc.spring.filter.logging.request.RequestBodyExtractor;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.request.SimpleRequestBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.SimpleResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.uritransform.TransformRequestUriMdcPopulatorFilter;
import uk.co.mruoc.spring.filter.logging.uritransform.UuidIdStringTransformer;

import java.time.Clock;
import java.util.Collection;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HeaderMdcPopulatorFilter> headerMdcPopulator() {
        FilterRegistrationBean<HeaderMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HeaderMdcPopulatorFilter("correlation-id", "channel-id"));
        bean.setOrder(1);
        bean.addUrlPatterns("*");
        bean.setName("headerMdcPopulator");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestMdcPopulatorFilter> requestMdcPopulator(Clock clock) {
        FilterRegistrationBean<RequestMdcPopulatorFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestMdcPopulatorFilter(clock));
        bean.setOrder(2);
        bean.addUrlPatterns("*");
        bean.setName("requestMdcPopulator");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> contextsRequestLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        RequestBodyExtractor extractor = new TransformingRequestBodyExtractor(StringFunctionComposer.compose(
                new ContextRequestPhoneNumberJsonMasker(mapper),
                new ContextRequestEmailAddressesJsonMasker(mapper)
        ));
        bean.setFilter(new RequestLoggingFilter(extractor));
        bean.setOrder(3);
        bean.addUrlPatterns(getContextsUrlPatterns());
        bean.setName("contextsRequestLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> contextsResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        ResponseBodyExtractor extractor = new TransformingResponseBodyExtractor(StringFunctionComposer.compose(
                new ContextResponsePhoneNumberJsonMasker(mapper),
                new ContextResponseEmailAddressJsonMasker(mapper)
        ));
        bean.setFilter(new ResponseLoggingFilter(extractor));
        bean.setOrder(4);
        bean.addUrlPatterns(getContextsUrlPatterns());
        bean.setName("contextsResponseLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> identitiesRequestLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        RequestBodyExtractor extractor = new TransformingRequestBodyExtractor(StringFunctionComposer.compose(
                new IdentityPhoneNumberJsonMasker(mapper),
                new IdentityEmailAddressesJsonMasker(mapper)
        ));
        bean.setFilter(new RequestLoggingFilter(extractor));
        bean.setOrder(3);
        bean.addUrlPatterns(getIdentitiesUrlPatterns());
        bean.setName("identitiesRequestLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> identitiesResponseLoggingFilter(ObjectMapper mapper) {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        ResponseBodyExtractor extractor = new TransformingResponseBodyExtractor(StringFunctionComposer.compose(
                new IdentityPhoneNumberJsonMasker(mapper),
                new IdentityEmailAddressesJsonMasker(mapper)
        ));
        bean.setFilter(new ResponseLoggingFilter(extractor));
        bean.setOrder(4);
        bean.addUrlPatterns(getIdentitiesUrlPatterns());
        bean.setName("identitiesResponseLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> defaultRequestLoggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new RequestLoggingFilter(new SimpleRequestBodyExtractor()));
        bean.setOrder(3);
        bean.addUrlPatterns(getDefaultUrlPatterns());
        bean.setName("defaultRequestLoggingFilter");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<ResponseLoggingFilter> defaultResponseLoggingFilter() {
        FilterRegistrationBean<ResponseLoggingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ResponseLoggingFilter(new SimpleResponseBodyExtractor()));
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

    private static String[] getContextsUrlPatterns() {
        return new String[]{
                "/contexts",
                "/contexts/*"
        };
    }

    private static String[] getIdentitiesUrlPatterns() {
        return new String[]{"/identities/*" };
    }

    private static String[] getDefaultUrlPatterns() {
        return new String[]{
                "/actuator/*",
                "/eligibility/*",
                "/lockout-policies/*",
                "/lockout-states/*",
                "/context-policies/*"
        };
    }

    private static class ContextRequestPhoneNumberJsonMasker extends JsonMasker {

        public ContextRequestPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths(), new PhoneNumberMaskFunction(), JsonPathConfig.build());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths("$.channel.phoneNumbers[*].value");
        }

    }

    private static class ContextRequestEmailAddressesJsonMasker extends JsonMasker {

        public ContextRequestEmailAddressesJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths(), new EmailAddressesMaskFunction(), JsonPathConfig.build());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths("$.channel.emailAddresses[*]");
        }

    }

    private static class ContextResponsePhoneNumberJsonMasker extends JsonMasker {

        public ContextResponsePhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths(), new PhoneNumberMaskFunction(), JsonPathConfig.build());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths(
                    "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='sms')].value",
                    "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='voice')].value",
                    "$.request.identity.phoneNumbers[*].value"
            );
        }

    }

    private static class ContextResponseEmailAddressJsonMasker extends JsonMasker {

        public ContextResponseEmailAddressJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths(), new EmailAddressesMaskFunction(), JsonPathConfig.build());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths(
                    "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.name=='email')].value",
                    "$.request.identity.emailAddresses[*]"
            );
        }

    }

    private static class IdentityPhoneNumberJsonMasker extends JsonMasker {

        public IdentityPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths(), new PhoneNumberMaskFunction(), JsonPathConfig.build());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths("$.phoneNumbers[*].value");
        }

    }

    private static class IdentityEmailAddressesJsonMasker extends JsonMasker {

        public IdentityEmailAddressesJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths(), new EmailAddressesMaskFunction(), JsonPathConfig.build());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths("$.emailAddresses[*]");
        }

    }

    private static class PhoneNumberMaskFunction extends MaskFunction {

        public PhoneNumberMaskFunction() {
            super(new IgnoreLastNCharsMasker(3));
        }

    }

    private static class EmailAddressesMaskFunction extends MaskFunction {

        public EmailAddressesMaskFunction() {
            super(new IgnoreFirstNAndLastNCharsMasker(3, 6));
        }

    }

}
