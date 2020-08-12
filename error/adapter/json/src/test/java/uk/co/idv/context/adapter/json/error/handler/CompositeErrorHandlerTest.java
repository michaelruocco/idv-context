package uk.co.idv.context.adapter.json.error.handler;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CompositeErrorHandlerTest {

    private final ErrorHandler defaultHandler = mock(ErrorHandler.class);
    private final ErrorHandler specificHandler = mock(ErrorHandler.class);

    private final ErrorHandler compositeHandler = new CompositeErrorHandler(defaultHandler, specificHandler);

    @Test
    void shouldSupportAnyThrowable() {
        Throwable any = new Throwable();

        assertThat(compositeHandler.supports(any)).isTrue();
    }

    @Test
    void shouldReturnErrorFromSpecificHandlerIfExceptionIsSupported() {
        Throwable exception = new Exception();
        given(specificHandler.supports(exception)).willReturn(true);
        ApiError expectedError = mock(ApiError.class);
        given(specificHandler.apply(exception)).willReturn(expectedError);

        ApiError error = compositeHandler.apply(exception);

        assertThat(error).isEqualTo(expectedError);
    }

    @Test
    void shouldReturnErrorFromDefaultHandlerIfNoSpecificHandlersSupportException() {
        Throwable exception = new Exception();
        given(specificHandler.supports(exception)).willReturn(false);
        ApiError expectedError = mock(ApiError.class);
        given(defaultHandler.apply(exception)).willReturn(expectedError);

        ApiError error = compositeHandler.apply(exception);

        assertThat(error).isEqualTo(expectedError);
    }

}
