package uk.co.idv.context.entities.policy.method.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;

class EmailDeliveryMethodConfigTest {

    private final DeliveryMethodConfig config = new EmailDeliveryMethodConfig();

    @Test
    void shouldReturnType() {
        assertThat(config.getType()).isEqualTo("email");
    }

}
