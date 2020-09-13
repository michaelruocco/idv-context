package uk.co.idv.context.entities.context.method.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OtpTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        Otp otp = Otp.builder()
                .name(name)
                .build();

        assertThat(otp.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnDeliveryMethods() {
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);

        Otp otp = Otp.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(otp.getDeliveryMethods()).isEqualTo(deliveryMethods);
    }

}
