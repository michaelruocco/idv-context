package uk.co.idv.context.adapter.json.error.handler;

import uk.co.idv.context.adapter.json.error.badrequest.InvalidJsonHandler;

public class CommonApiErrorHandler extends CompositeErrorHandler {

    public CommonApiErrorHandler() {
        super(new InvalidJsonHandler());
    }

}
