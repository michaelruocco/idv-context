package uk.co.idv.context.usecases.identity.create;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryNotProvidedExceptionTest {

    @Test
    void shouldReturnMessage() {
        Throwable error = new CountryNotProvidedException();

        assertThat(error.getMessage()).isEqualTo("cannot create an identity without a country");
    }

}
