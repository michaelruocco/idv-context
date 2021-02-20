package uk.co.idv.context.adapter.verification.client.exception;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ApiErrorClientExceptionTest {

    @Test
    void shouldReturnApiError() {
        ApiError error =  mock(ApiError.class);

        ApiErrorClientException exception = new ApiErrorClientException(error);

        assertThat(exception.getError()).isEqualTo(error);
    }

    @Test
    void shouldReturnErrorMessageFromApiError() {
        String expectedMessage = "error-message";
        ApiError error =  givenApiErrorWithMessage(expectedMessage);

        Throwable throwable = new ApiErrorClientException(error);

        assertThat(throwable.getMessage()).isEqualTo(expectedMessage);
    }

    private ApiError givenApiErrorWithMessage(String message) {
        ApiError error = mock(ApiError.class);
        given(error.getMessage()).willReturn(message);
        return error;
    }

}
