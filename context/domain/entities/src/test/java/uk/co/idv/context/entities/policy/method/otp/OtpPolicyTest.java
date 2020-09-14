package uk.co.idv.context.entities.policy.method.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.identity.entities.identity.RequestedData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpPolicyTest {

    private final DeliveryMethodConfigs deliveryMethodConfigs = mock(DeliveryMethodConfigs.class);
    private final OtpConfig otpConfig = mock(OtpConfig.class);

    private final OtpPolicy policy = OtpPolicy.builder()
            .methodConfig(otpConfig)
            .deliveryMethodConfigs(deliveryMethodConfigs)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(policy.getName()).isEqualTo("one-time-passcode");
    }

    @Test
    void shouldReturnMethodConfig() {
        assertThat(policy.getMethodConfig()).isEqualTo(otpConfig);
    }

    @Test
    void shouldReturnDeliveryMethodConfigs() {
        assertThat(policy.getDeliveryMethodConfigs()).isEqualTo(deliveryMethodConfigs);
    }

    @Test
    void shouldReturnRequestedDataFromConfigs() {
        RequestedData expectedRequestedData = mock(RequestedData.class);
        given(deliveryMethodConfigs.getRequestedData()).willReturn(expectedRequestedData);

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

}
