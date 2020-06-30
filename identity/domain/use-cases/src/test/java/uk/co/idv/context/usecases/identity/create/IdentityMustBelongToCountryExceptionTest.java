package uk.co.idv.context.usecases.identity.create;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityMustBelongToCountryExceptionTest {

    @Test
    void shouldReturnMessage() {
        Throwable error = new IdentityMustBelongToCountryException();

        assertThat(error.getMessage()).isEqualTo("identity must belong to country");
    }

}
