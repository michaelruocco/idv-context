package uk.co.idv.app.spring;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ApplicationErrorHandlerTest {

    private final ErrorHandler errorHandler = mock(ErrorHandler.class);

    private final ApplicationErrorHandler handler = new ApplicationErrorHandler(errorHandler);

    @Test
    void shouldConvertThrowableToResponseEntityWithError() {
        HttpStatus status = HttpStatus.CREATED;
        Throwable cause = new Throwable();
        givenConvertedToErrorWithStatus(cause, status);

        ResponseEntity<ApiError> response = handler.handleException(cause);

        assertThat(response.getStatusCode()).isEqualTo(status);
    }

    private void givenConvertedToErrorWithStatus(Throwable cause, HttpStatus status) {
        ApiError error = mock(ApiError.class);
        given(error.getStatus()).willReturn(status.value());
        given(errorHandler.apply(cause)).willReturn(Optional.of(error));
    }

}
