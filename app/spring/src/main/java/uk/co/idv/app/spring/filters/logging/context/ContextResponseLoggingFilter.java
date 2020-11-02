package uk.co.idv.app.spring.filters.logging.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.app.spring.filters.logging.masking.emailaddress.EmailAddressJsonMasker;
import uk.co.idv.app.spring.filters.logging.masking.phonenumber.PhoneNumberJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;

import java.util.Collection;

public class ContextResponseLoggingFilter extends ResponseLoggingFilter {

    public ContextResponseLoggingFilter(ObjectMapper mapper) {
        super(buildResponseBodyExtractor(mapper));
    }

    private static ResponseBodyExtractor buildResponseBodyExtractor(ObjectMapper mapper) {
        return new TransformingResponseBodyExtractor(StringFunctionComposer.compose(
                new PhoneNumberJsonMasker(mapper, phoneNumberPaths()),
                new EmailAddressJsonMasker(mapper, emailAddressPaths())
        ));
    }

    private static Collection<JsonPath> phoneNumberPaths() {
        return JsonPathFactory.toJsonPaths(
                "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='sms')].value",
                "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='voice')].value",
                "$.request.identity.phoneNumbers[*].value"
        );
    }

    private static Collection<JsonPath> emailAddressPaths() {
        return JsonPathFactory.toJsonPaths(
                "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.name=='email')].value",
                "$.request.identity.emailAddresses[*]"
        );
    }


}
