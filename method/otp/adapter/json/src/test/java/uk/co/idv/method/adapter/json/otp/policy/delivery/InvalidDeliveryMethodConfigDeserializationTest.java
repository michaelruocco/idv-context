package uk.co.idv.method.adapter.json.otp.policy.delivery;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidDeliveryMethodConfigDeserializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new DeliveryMethodConfigModule());
    private static final String JSON = InvalidDeliveryMethodConfigJsonMother.invalid();

    @Test
    void shouldThrowErrorOnDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, DeliveryMethodConfig.class));

        assertThat(error)
                .isInstanceOf(InvalidDeliveryMethodConfigTypeException.class)
                .hasMessage("invalid");
    }

}
