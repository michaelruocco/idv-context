package uk.co.idv.app.spring.filters.logging.common;

import uk.co.mruoc.spring.filter.logging.response.ResponseLoggingFilter;
import uk.co.mruoc.spring.filter.logging.response.SimpleResponseBodyExtractor;

public class DefaultResponseLoggingFilter extends ResponseLoggingFilter {

    public DefaultResponseLoggingFilter() {
        super(new SimpleResponseBodyExtractor());
    }

}
