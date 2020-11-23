package uk.co.idv.app.spring.filters.logging.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.app.spring.filters.masking.phonenumber.ContextPhoneNumberResponseJsonMasker;
import uk.co.idv.app.spring.filters.masking.emailaddress.ContextEmailAddressResponseJsonMasker;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;

public class ContextResponseLoggingFilter extends ResponseLoggingFilter {

    public ContextResponseLoggingFilter(ObjectMapper mapper) {
        super(buildResponseBodyExtractor(mapper));
    }

    private static ResponseBodyExtractor buildResponseBodyExtractor(ObjectMapper mapper) {
        return new TransformingResponseBodyExtractor(StringFunctionComposer.compose(
                new ContextPhoneNumberResponseJsonMasker(mapper),
                new ContextEmailAddressResponseJsonMasker(mapper)
        ));
    }

}
