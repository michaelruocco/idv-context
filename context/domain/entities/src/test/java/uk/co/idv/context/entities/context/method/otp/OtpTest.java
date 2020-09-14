package uk.co.idv.context.entities.context.method.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
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
        DeliveryMethods deliveryMethods = givenDeliveryMethods();

        Otp otp = Otp.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(otp.getDeliveryMethods()).isEqualTo(deliveryMethods);
    }

    @Test
    void shouldReturnEligibilityFromDeliveryMethods() {
        Eligibility eligibility = mock(Eligibility.class);
        DeliveryMethods deliveryMethods = givenDeliveryMethodsWithEligibility(eligibility);

        Otp otp = Otp.builder()
                .deliveryMethods(deliveryMethods)
                .build();

        assertThat(otp.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldNotBeComplete() {
        Otp otp = Otp.builder()
                .build();

        assertThat(otp.isComplete()).isFalse();
    }

    @Test
    void shouldNotBeSuccessful() {
        Otp otp = Otp.builder()
                .build();

        assertThat(otp.isSuccessful()).isFalse();
    }

    private DeliveryMethods givenDeliveryMethodsWithEligibility(Eligibility eligibility) {
        DeliveryMethods deliveryMethods = givenDeliveryMethods();
        given(deliveryMethods.getEligibility()).willReturn(eligibility);
        return deliveryMethods;
    }

    private DeliveryMethods givenDeliveryMethods() {
        return mock(DeliveryMethods.class);
    }

}
