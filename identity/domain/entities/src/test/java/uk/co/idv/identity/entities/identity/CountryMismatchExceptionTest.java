package uk.co.idv.identity.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMismatchExceptionTest {

    private final CountryCode existing = CountryCode.DE;
    private final CountryCode updated = CountryCode.GB;

    private final CountryMismatchException error = new CountryMismatchException(existing, updated);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("cannot merge identities if countries do not match");
    }

    @Test
    void shouldReturnExistingCountry() {
        assertThat(error.getExisting()).isEqualTo(existing);
    }

    @Test
    void shouldReturnUpdatedCountry() {
        assertThat(error.getUpdated()).isEqualTo(updated);
    }

}
