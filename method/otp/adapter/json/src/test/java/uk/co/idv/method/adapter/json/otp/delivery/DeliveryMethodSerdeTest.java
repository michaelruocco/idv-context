package uk.co.idv.method.adapter.json.otp.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new DeliveryMethodModule());

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
