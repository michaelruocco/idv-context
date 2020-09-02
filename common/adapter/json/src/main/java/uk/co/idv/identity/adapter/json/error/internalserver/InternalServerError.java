package uk.co.idv.identity.adapter.json.error.internalserver;

import uk.co.idv.identity.adapter.json.error.DefaultApiError;

import java.util.Collections;

public class InternalServerError extends DefaultApiError {

    private static final int STATUS = 500;
    private static final String TITLE = "Internal server error";

    public InternalServerError(String message) {
        super(STATUS, TITLE, message, Collections.emptyMap());
    }

}
