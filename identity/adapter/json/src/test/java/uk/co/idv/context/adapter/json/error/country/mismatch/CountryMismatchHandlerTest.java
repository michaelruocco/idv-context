package uk.co.idv.context.adapter.json.error.country.mismatch;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.identity.CountryMismatchException;
import uk.co.idv.context.entities.identity.CountryMismatchExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMismatchHandlerTest {

    private final ErrorHandler handler = new CountryMismatchHandler();

    @Test
    void shouldSupportCountryMismatchException() {
        CountryMismatchException exception = CountryMismatchExceptionMother.build();

        assertThat(handler.apply(exception)).isPresent();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.apply(other)).isEmpty();
    }

    @Test
    void shouldReturnCountryMismatchError() {
        CountryMismatchException cause = CountryMismatchExceptionMother.build();

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).containsInstanceOf(CountryMismatchError.class);
    }

    @Test
    void shouldPopulateUpdatedAndExistingIdvIds() {
        CountryMismatchException cause = CountryMismatchExceptionMother.build();

        Optional<ApiError> error = handler.apply(cause);

        assertThat(error).isPresent();
        CountryMismatchError specificError = (CountryMismatchError) error.get();
        assertThat(specificError.getUpdated()).isEqualTo(cause.getUpdated());
        assertThat(specificError.getExisting()).isEqualTo(cause.getExisting());
    }

}
