package uk.co.idv.identity.adapter.json.error.badrequest;

import uk.co.idv.identity.adapter.json.error.DefaultApiError;

import java.util.Collections;

public class BadRequestError extends DefaultApiError {

    private static final int STATUS = 400;
    private static final String TITLE = "Bad request";

    public BadRequestError(String message) {
        super(STATUS, TITLE, message, Collections.emptyMap());
    }

}
