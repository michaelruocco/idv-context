package uk.co.idv.context.entities.policy.method.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryConfig;

import static org.assertj.core.api.Assertions.assertThat;

class EmailDeliveryConfigTest {

    private final DeliveryConfig config = new EmailDeliveryConfig();

    @Test
    void shouldReturnName() {
        assertThat(config.getName()).isEqualTo("email");
    }

}
