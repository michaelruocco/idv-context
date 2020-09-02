package uk.co.idv.common.adapter.json.error.handler;

import uk.co.idv.common.adapter.json.error.ApiError;

import java.util.Optional;
import java.util.function.Function;

public interface ErrorHandler extends Function<Throwable, Optional<ApiError>> {

    // intentionally blank

}
