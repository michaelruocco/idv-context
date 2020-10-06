package uk.co.idv.method.entities.otp.policy.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;

class EmailDeliveryMethodConfigTest {

    private final DeliveryMethodConfig config = new EmailDeliveryMethodConfig();

    @Test
    void shouldReturnType() {
        assertThat(config.getType()).isEqualTo("email");
    }

    @Test
    void shouldRequestEmailAddresses() {
        RequestedData requestedData = config.getRequestedData();

        assertThat(requestedData).isEqualTo(RequestedDataMother.emailAddressesOnly());
    }

}
