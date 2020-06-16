package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateNotConfiguredForChannelExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new UpdateNotConfiguredForChannelException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
