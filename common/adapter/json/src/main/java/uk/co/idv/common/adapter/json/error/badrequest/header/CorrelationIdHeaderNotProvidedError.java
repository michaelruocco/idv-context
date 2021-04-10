package uk.co.idv.common.adapter.json.error.badrequest.header;

import uk.co.idv.common.adapter.json.error.badrequest.BadRequestError;

public class CorrelationIdHeaderNotProvidedError extends BadRequestError {

    private static final String MESSAGE = "mandatory correlation-id header not provided";

    public CorrelationIdHeaderNotProvidedError() {
        super(MESSAGE);
    }

}
