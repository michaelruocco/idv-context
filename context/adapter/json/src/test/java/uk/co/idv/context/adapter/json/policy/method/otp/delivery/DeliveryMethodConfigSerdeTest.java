package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodConfigSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new DeliveryMethodConfigModule());

    @ParameterizedTest(name = "should serialize delivery method config {1}")
    @ArgumentsSource(DeliveryMethodConfigArgumentsProvider.class)
    void shouldSerialize(String expectedJson, DeliveryMethodConfig config) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(config);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize delivery method config {1}")
    @ArgumentsSource(DeliveryMethodConfigArgumentsProvider.class)
    void shouldDeserialize(String json, DeliveryMethodConfig expectedConfig) throws JsonProcessingException {
        DeliveryMethodConfig config = MAPPER.readValue(json, DeliveryMethodConfig.class);

        assertThat(config).isEqualTo(expectedConfig);
    }

}
