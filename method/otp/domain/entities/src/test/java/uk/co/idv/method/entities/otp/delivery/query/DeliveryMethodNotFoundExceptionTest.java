package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodNotFoundExceptionTest {

    @Test
    void shouldReturnIdAsMessage() {
        UUID id = UUID.fromString("d3831048-957d-4441-93f9-9a9b6b8212f6");

        Throwable error = new DeliveryMethodNotFoundException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
