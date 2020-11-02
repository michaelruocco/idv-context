package uk.co.idv.app.spring.filters.logging.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.app.spring.filters.logging.masking.emailaddress.EmailAddressJsonMasker;
import uk.co.idv.app.spring.filters.logging.masking.phonenumber.PhoneNumberJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.request.RequestBodyExtractor;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.request.TransformingRequestBodyExtractor;

import java.util.Collection;

public class ContextRequestLoggingFilter extends RequestLoggingFilter {

    public ContextRequestLoggingFilter(ObjectMapper mapper) {
        super(buildRequestBodyExtractor(mapper));
    }

    private static RequestBodyExtractor buildRequestBodyExtractor(ObjectMapper mapper) {
        return new TransformingRequestBodyExtractor(StringFunctionComposer.compose(
                new PhoneNumberJsonMasker(mapper, phoneNumberPaths()),
                new EmailAddressJsonMasker(mapper, emailAddressPaths())
        ));
    }

    private static Collection<JsonPath> phoneNumberPaths() {
        return JsonPathFactory.toJsonPaths("$.channel.phoneNumbers[*].value");
    }

    private static Collection<JsonPath> emailAddressPaths() {
        return JsonPathFactory.toJsonPaths("$.channel.emailAddresses[*]");
    }

}
