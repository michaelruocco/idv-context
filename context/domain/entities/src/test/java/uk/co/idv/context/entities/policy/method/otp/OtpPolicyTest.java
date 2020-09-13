package uk.co.idv.context.entities.policy.method.otp;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.identity.entities.identity.RequestedData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OtpPolicyTest {

    private final DeliveryMethodConfigs configs = mock(DeliveryMethodConfigs.class);

    private final OtpPolicy policy = new OtpPolicy(configs);

    @Test
    void shouldReturnName() {
        assertThat(policy.getName()).isEqualTo("one-time-passcode");
    }

    @Test
    void shouldDeliveryMethodConfigs() {
        assertThat(policy.getDeliveryMethodConfigs()).isEqualTo(configs);
    }

    @Test
    void shouldReturnRequestedDataFromConfigs() {
        RequestedData expectedRequestedData = mock(RequestedData.class);
        given(configs.getRequestedData()).willReturn(expectedRequestedData);

        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(expectedRequestedData);
    }

}
