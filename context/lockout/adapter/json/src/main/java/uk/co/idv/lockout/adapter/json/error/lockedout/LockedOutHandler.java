package uk.co.idv.lockout.adapter.json.error.lockedout;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.AbstractErrorHandler;
import uk.co.idv.lockout.entities.LockedOutException;

@Slf4j
public class LockedOutHandler extends AbstractErrorHandler {

    @Override
    protected boolean supports(Throwable cause) {
        return LockedOutException.class.isAssignableFrom(cause.getClass());
    }

    @Override
    protected ApiError toError(Throwable cause) {
        LockedOutException error = (LockedOutException) cause;
        return new LockedOutError(error.getState());
    }

}
