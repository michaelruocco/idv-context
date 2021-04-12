package uk.co.idv.common.adapter.json.error.badrequest.header;

import uk.co.idv.common.adapter.json.error.badrequest.BadRequestError;

public class ChannelIdHeaderNotProvidedError extends BadRequestError {

    private static final String MESSAGE = "mandatory channel-id header not provided";

    public ChannelIdHeaderNotProvidedError() {
        super(MESSAGE);
    }

}
