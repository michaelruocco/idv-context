package uk.co.idv.common.adapter.json.error.handler;

import uk.co.idv.common.adapter.json.error.badrequest.InvalidJsonHandler;

public class CommonApiErrorHandler extends CompositeErrorHandler {

    public CommonApiErrorHandler() {
        super(new InvalidJsonHandler());
    }

}
