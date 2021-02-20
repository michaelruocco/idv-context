package uk.co.idv.identity.adapter.json.error.country.notprovided;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.entities.identity.CountryNotProvidedException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CountryNotProvidedHandlerTest {

    private final ErrorHandler handler = new CountryNotProvidedHandler();

    @Test
    void shouldConvertCountryNotProvidedExceptionToError() {
        CountryNotProvidedException exception = mock(CountryNotProvidedException.class);

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(CountryNotProvidedError.class);
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
