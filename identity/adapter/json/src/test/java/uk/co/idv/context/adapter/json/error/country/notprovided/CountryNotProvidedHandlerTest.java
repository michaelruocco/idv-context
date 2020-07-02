package uk.co.idv.context.adapter.json.error.country.notprovided;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.usecases.identity.create.CountryNotProvidedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CountryNotProvidedHandlerTest {

    private final ErrorHandler handler = new CountryNotProvidedHandler();

    @Test
    void shouldSupportCountryNotProvidedException() {
        CountryNotProvidedException exception = mock(CountryNotProvidedException.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    public void shouldReturnCountryMismatchError() {
        CountryNotProvidedException cause = mock(CountryNotProvidedException.class);

        ApiError error = handler.apply(cause);

        assertThat(error).isInstanceOf(CountryNotProvidedError.class);
    }

}
