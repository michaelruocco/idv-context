package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .registerModule(new DeliveryMethodModule());

    @ParameterizedTest(name = "should serialize delivery method {1}")
    @ArgumentsSource(DeliveryMethodArgumentsProvider.class)
    void shouldSerialize(String expectedJson, DeliveryMethod deliveryMethod) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(deliveryMethod);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize delivery method {1}")
    @ArgumentsSource(DeliveryMethodArgumentsProvider.class)
    void shouldDeserialize(String json, DeliveryMethod expectedDeliveryMethod) throws JsonProcessingException {
        DeliveryMethod deliveryMethod = MAPPER.readValue(json, DeliveryMethod.class);

        assertThat(deliveryMethod).isEqualTo(expectedDeliveryMethod);
    }

}
