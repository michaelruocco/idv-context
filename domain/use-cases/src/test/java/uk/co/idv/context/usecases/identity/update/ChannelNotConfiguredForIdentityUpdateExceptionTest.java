package uk.co.idv.context.usecases.identity.update;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelNotConfiguredForIdentityUpdateExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new ChannelNotConfiguredForIdentityUpdateException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
