package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyAliasesExceptionTest {

    @Test
    void shouldReturnMessage() {
        Throwable error = new EmptyAliasesException();

        assertThat(error.getMessage()).isEqualTo("cannot get first value of empty aliases");
    }

}
