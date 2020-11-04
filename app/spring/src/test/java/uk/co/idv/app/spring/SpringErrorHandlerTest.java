package uk.co.idv.app.spring;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.app.manual.Application;
import uk.co.idv.common.adapter.json.error.ApiError;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SpringErrorHandlerTest {

    private final Application application = mock(Application.class);

    private final SpringErrorHandler handler = new SpringErrorHandler(application);

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
        given(application.handle(cause)).willReturn(Optional.of(error));
    }

}
