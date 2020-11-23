package uk.co.idv.app.spring.filters.masking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextResponseMaskingFilter extends RewriteResponseBodyFilter {

    public ContextResponseMaskingFilter(ObjectMapper mapper) {
        super(new MaskContextResponse(mapper));
    }

}
