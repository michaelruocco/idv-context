package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethodsMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodsSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .registerModule(new DeliveryMethodModule());
    private static final DeliveryMethods DELIVERY_METHODS = DeliveryMethodsMother.oneOfEach();
    private static final String JSON = DeliveryMethodsJsonMother.oneOfEach();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(DELIVERY_METHODS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        DeliveryMethods deliveryMethods = MAPPER.readValue(JSON, DeliveryMethods.class);

        assertThat(deliveryMethods).isEqualTo(DELIVERY_METHODS);
    }

}
