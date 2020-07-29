package uk.co.idv.app.spring;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class ApplicationErrorHandler {

    private final ErrorHandler errorHandler;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleException(Throwable throwable) {
        return toResponseEntity(errorHandler.apply(throwable));
    }

    private static ResponseEntity<ApiError> toResponseEntity(ApiError error) {
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getStatus()));
    }

}
