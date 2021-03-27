package uk.co.idv.app.spring.filters.logging.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.method.adapter.json.verification.mask.VerificationJsonMasker;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;

public class VerificationResponseLoggingFilter extends ResponseLoggingFilter {

    public VerificationResponseLoggingFilter(ObjectMapper mapper) {
        super(buildResponseBodyExtractor(mapper));
    }

    private static ResponseBodyExtractor buildResponseBodyExtractor(ObjectMapper mapper) {
        return new TransformingResponseBodyExtractor(new VerificationJsonMasker(mapper));
    }

}
