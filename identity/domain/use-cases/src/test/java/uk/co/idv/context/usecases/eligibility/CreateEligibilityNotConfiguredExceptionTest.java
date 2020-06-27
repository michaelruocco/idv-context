package uk.co.idv.context.usecases.eligibility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateEligibilityNotConfiguredExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new CreateEligibilityNotConfiguredException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
