package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMismatchExceptionTest {

    private final CountryCode existingCountry = CountryCode.DE;
    private final CountryCode countryToAdd = CountryCode.GB;

    private final CountryMismatchException error = new CountryMismatchException(existingCountry, countryToAdd);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("countries do not match");
    }

    @Test
    void shouldReturnExistingCountry() {
        assertThat(error.getExistingCountry()).isEqualTo(existingCountry);
    }

    @Test
    void shouldReturnCountryToAdd() {
        assertThat(error.getCountryToAdd()).isEqualTo(countryToAdd);
    }

}
