package uk.co.idv.app.spring.filters.logging.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.identity.adapter.json.identity.mask.IdentityEmailAddressJsonMasker;
import uk.co.idv.identity.adapter.json.identity.mask.IdentityPhoneNumberJsonMasker;
import uk.co.mruoc.spring.filter.logging.StringFunctionComposer;
import uk.co.mruoc.spring.filter.logging.request.RequestBodyExtractor;
import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.request.TransformingRequestBodyExtractor;

public class IdentityRequestLoggingFilter extends RequestLoggingFilter {

    public IdentityRequestLoggingFilter(ObjectMapper mapper) {
        super(buildRequestBodyExtractor(mapper));
    }

    private static RequestBodyExtractor buildRequestBodyExtractor(ObjectMapper mapper) {
        return new TransformingRequestBodyExtractor(StringFunctionComposer.compose(
                new IdentityPhoneNumberJsonMasker(mapper),
                new IdentityEmailAddressJsonMasker(mapper)
        ));
    }

}
