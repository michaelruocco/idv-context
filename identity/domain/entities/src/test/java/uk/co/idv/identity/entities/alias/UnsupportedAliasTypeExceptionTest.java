package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnsupportedAliasTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String type = "my-alias-type";

        Throwable error = new UnsupportedAliasTypeException(type);

        assertThat(error.getMessage()).isEqualTo(type);
    }

}
