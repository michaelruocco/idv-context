package uk.co.idv.context.adapter.json.error.handler;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeErrorHandlerTest {

    private final ErrorHandler specificHandler1 = mock(ErrorHandler.class);
    private final ErrorHandler specificHandler2 = mock(ErrorHandler.class);

    private final ErrorHandler compositeHandler = new CompositeErrorHandler(specificHandler1, specificHandler2);

    @Test
    void shouldReturnErrorFromSpecificHandlerIfReturned() {
        Throwable exception = new Exception();
        given(specificHandler1.apply(exception)).willReturn(Optional.empty());
        ApiError expectedError = mock(ApiError.class);
        given(specificHandler2.apply(exception)).willReturn(Optional.of(expectedError));

        Optional<ApiError> error = compositeHandler.apply(exception);

        assertThat(error).contains(expectedError);
    }

    @Test
    void shouldReturnEmptyOptionalIfNoSpecificHandlersReturnError() {
        Throwable exception = new Exception();
        given(specificHandler1.apply(exception)).willReturn(Optional.empty());
        given(specificHandler2.apply(exception)).willReturn(Optional.empty());

        Optional<ApiError> error = compositeHandler.apply(exception);

        assertThat(error).isEmpty();
    }

}
