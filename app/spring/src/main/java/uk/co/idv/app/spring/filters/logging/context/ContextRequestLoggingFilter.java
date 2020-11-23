package uk.co.idv.app.spring.filters.logging.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.app.spring.filters.masking.emailaddress.ContextEmailAddressRequestJsonMasker;
import uk.co.idv.app.spring.filters.masking.phonenumber.ContextPhoneNumberRequestJsonMasker;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.request.RequestBodyExtractor;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.request.TransformingRequestBodyExtractor;

public class ContextRequestLoggingFilter extends RequestLoggingFilter {

    public ContextRequestLoggingFilter(ObjectMapper mapper) {
        super(buildRequestBodyExtractor(mapper));
    }

    private static RequestBodyExtractor buildRequestBodyExtractor(ObjectMapper mapper) {
        return new TransformingRequestBodyExtractor(StringFunctionComposer.compose(
                new ContextPhoneNumberRequestJsonMasker(mapper),
                new ContextEmailAddressRequestJsonMasker(mapper)
        ));
    }



}
