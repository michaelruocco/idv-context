package uk.co.idv.identity.entities.eligibility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EligibilityNotConfiguredExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new EligibilityNotConfiguredException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
