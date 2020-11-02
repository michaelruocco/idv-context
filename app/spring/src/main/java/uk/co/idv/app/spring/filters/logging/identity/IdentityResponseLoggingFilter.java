package uk.co.idv.app.spring.filters.logging.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;

public class IdentityResponseLoggingFilter extends ResponseLoggingFilter {

    public IdentityResponseLoggingFilter(ObjectMapper mapper) {
        super(buildResponseBodyExtractor(mapper));
    }

    private static ResponseBodyExtractor buildResponseBodyExtractor(ObjectMapper mapper) {
        return new TransformingResponseBodyExtractor(StringFunctionComposer.compose(
                new IdentityPhoneNumberJsonMasker(mapper),
                new IdentityEmailAddressJsonMasker(mapper)
        ));
    }

}
