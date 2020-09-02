package uk.co.idv.identity.adapter.json.error.country.mismatch;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.common.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.identity.entities.identity.CountryMismatchException;
import uk.co.idv.identity.entities.identity.CountryMismatchExceptionMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMismatchHandlerTest {

    private final ErrorHandler handler = new CountryMismatchHandler();

    @Test
    void shouldConvertSupportCountryMismatchExceptionToError() {
        CountryMismatchException exception = CountryMismatchExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).containsInstanceOf(CountryMismatchError.class);
    }

    @Test
    void shouldPopulateUpdatedIdvId() {
        CountryMismatchException exception = CountryMismatchExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(e -> (CountryMismatchError) e)
                .map(CountryMismatchError::getUpdated)
                .contains(exception.getUpdated());
    }

    @Test
    void shouldPopulateExistingIdvId() {
        CountryMismatchException exception = CountryMismatchExceptionMother.build();

        Optional<ApiError> error = handler.apply(exception);

        assertThat(error).map(e -> (CountryMismatchError) e)
                .map(CountryMismatchError::getExisting)
                .contains(exception.getExisting());
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        Optional<ApiError> error = handler.apply(other);

        assertThat(error).isEmpty();
    }

}
