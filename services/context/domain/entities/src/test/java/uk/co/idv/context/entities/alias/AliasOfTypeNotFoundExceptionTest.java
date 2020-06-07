package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AliasOfTypeNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        String type = "my-alias-type";

        Throwable error = new AliasOfTypeNotFoundException(type);

        assertThat(error.getMessage()).isEqualTo(type);
    }

}
