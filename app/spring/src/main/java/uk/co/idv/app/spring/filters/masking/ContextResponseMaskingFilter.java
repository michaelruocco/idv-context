package uk.co.idv.app.spring.filters.masking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.spring.filter.rewrite.RewriteResponseBodyFilter;

@Slf4j
public class ContextResponseMaskingFilter extends RewriteResponseBodyFilter {

    public ContextResponseMaskingFilter(ObjectMapper mapper) {
        super(new MaskContextResponse(mapper));
    }

}
