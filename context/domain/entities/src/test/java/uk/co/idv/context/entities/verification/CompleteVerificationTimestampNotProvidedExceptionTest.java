package uk.co.idv.context.entities.verification;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CompleteVerificationTimestampNotProvidedExceptionTest {

    @Test
    void shouldReturnIdAsMessage() {
        UUID id = UUID.randomUUID();

        Throwable error = new CompleteVerificationTimestampNotProvidedException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
