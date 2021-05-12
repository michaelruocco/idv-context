package uk.co.idv.method.entities.push.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesOnly;
import uk.co.idv.method.entities.push.PushNotificationConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PushNotificationPolicyTest {

    private final PushNotificationConfig config = mock(PushNotificationConfig.class);

    private final PushNotificationPolicy policy = PushNotificationPolicy.builder()
            .config(config)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(policy.getName()).isEqualTo("push-notification");
    }

    @Test
    void shouldReturnMethodConfig() {
        assertThat(policy.getConfig()).isEqualTo(config);
    }

    @Test
    void shouldReturnRequestedData() {
        RequestedData requestedData = policy.getRequestedData();

        assertThat(requestedData).isEqualTo(new MobileDevicesOnly());
    }

}
