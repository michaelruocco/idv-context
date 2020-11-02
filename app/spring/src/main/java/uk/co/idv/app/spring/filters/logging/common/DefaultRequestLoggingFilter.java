package uk.co.idv.app.spring.filters.logging.common;

import uk.co.mruoc.spring.filter.logging.request.RequestLoggingFilter;
import uk.co.mruoc.spring.filter.logging.request.SimpleRequestBodyExtractor;

public class DefaultRequestLoggingFilter extends RequestLoggingFilter {

    public DefaultRequestLoggingFilter() {
        super(new SimpleRequestBodyExtractor());
    }

}
