package uk.co.idv.app.spring.filters.logging.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.context.adapter.json.context.create.mask.FacadeCreateContextRequestEmailAddressJsonMasker;
import uk.co.idv.context.adapter.json.context.create.mask.FacadeCreateContextRequestPhoneNumberJsonMasker;
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
                new FacadeCreateContextRequestPhoneNumberJsonMasker(mapper),
                new FacadeCreateContextRequestEmailAddressJsonMasker(mapper)
        ));
    }



}
