package uk.co.idv.context.adapter.json.error.handler;

import uk.co.idv.context.adapter.json.error.ApiError;

import java.util.function.Function;

public interface ErrorHandler extends Function<Throwable, ApiError> {

    boolean supports(Throwable throwable);

}
