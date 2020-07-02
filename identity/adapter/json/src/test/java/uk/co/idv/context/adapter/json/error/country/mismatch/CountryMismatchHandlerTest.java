package uk.co.idv.context.adapter.json.error.country.mismatch;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.entities.identity.CountryMismatchException;
import uk.co.idv.context.entities.identity.CountryMismatchExceptionMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CountryMismatchHandlerTest {

    private final ErrorHandler handler = new CountryMismatchHandler();

    @Test
    void shouldSupportCountryMismatchException() {
        CountryMismatchException exception = mock(CountryMismatchException.class);

        assertThat(handler.supports(exception)).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherException() {
        Throwable other = new Throwable();

        assertThat(handler.supports(other)).isFalse();
    }

    @Test
    public void shouldReturnCountryMismatchError() {
        CountryMismatchException cause = CountryMismatchExceptionMother.build();

        ApiError error = handler.apply(cause);

        assertThat(error).isInstanceOf(CountryMismatchError.class);
    }

    @Test
    public void shouldPopulateUpdatedAndExistingIdvIds() {
        CountryMismatchException cause = CountryMismatchExceptionMother.build();

        CountryMismatchError error = (CountryMismatchError) handler.apply(cause);

        assertThat(error.getUpdated()).isEqualTo(cause.getUpdated());
        assertThat(error.getExisting()).isEqualTo(cause.getExisting());
    }

}
