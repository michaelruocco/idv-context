package uk.co.idv.identity.adapter.json.error.handler;

import uk.co.idv.identity.adapter.json.error.badrequest.InvalidJsonHandler;

public class CommonApiErrorHandler extends CompositeErrorHandler {

    public CommonApiErrorHandler() {
        super(new InvalidJsonHandler());
    }

}
