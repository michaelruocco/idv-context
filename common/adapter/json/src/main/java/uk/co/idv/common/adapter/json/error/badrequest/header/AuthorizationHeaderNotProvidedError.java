package uk.co.idv.common.adapter.json.error.badrequest.header;

import uk.co.idv.common.adapter.json.error.badrequest.BadRequestError;

public class AuthorizationHeaderNotProvidedError extends BadRequestError {

    private static final String MESSAGE = "mandatory authorization header not provided";

    public AuthorizationHeaderNotProvidedError() {
        super(MESSAGE);
    }

}
