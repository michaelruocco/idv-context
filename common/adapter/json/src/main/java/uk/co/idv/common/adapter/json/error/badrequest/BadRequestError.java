package uk.co.idv.common.adapter.json.error.badrequest;

import uk.co.idv.common.adapter.json.error.DefaultApiError;

import java.util.Collections;
import java.util.Map;

public class BadRequestError extends DefaultApiError {

    private static final int STATUS = 400;
    private static final String TITLE = "Bad request";

    public BadRequestError(String message) {
        this(message, Collections.emptyMap());
    }

    public BadRequestError(String message, Map<String, Object> meta) {
        super(STATUS, TITLE, message, meta);
    }

}
