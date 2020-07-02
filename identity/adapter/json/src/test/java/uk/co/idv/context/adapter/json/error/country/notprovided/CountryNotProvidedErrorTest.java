package uk.co.idv.context.adapter.json.error.country.notprovided;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class CountryNotProvidedErrorTest {

    private final ApiError error = new CountryNotProvidedError();

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(400);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Cannot create an identity without a country");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEmpty();
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
