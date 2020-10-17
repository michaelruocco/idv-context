package uk.co.idv.identity.adapter.eligibility.external.data.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidAliasValueExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception();

        Throwable error = new InvalidAliasValueException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
