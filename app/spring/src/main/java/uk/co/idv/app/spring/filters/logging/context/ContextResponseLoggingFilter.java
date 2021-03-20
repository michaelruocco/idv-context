package uk.co.idv.app.spring.filters.logging.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.context.adapter.json.context.mask.ApiContextJsonMasker;
import uk.co.mruoc.spring.filter.logging.response.ResponseBodyExtractor;
import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.TransformingResponseBodyExtractor;

public class ContextResponseLoggingFilter extends ResponseLoggingFilter {

    public ContextResponseLoggingFilter(ObjectMapper mapper) {
        super(buildResponseBodyExtractor(mapper));
    }

    private static ResponseBodyExtractor buildResponseBodyExtractor(ObjectMapper mapper) {
        return new TransformingResponseBodyExtractor(new ApiContextJsonMasker(mapper));
    }

}
