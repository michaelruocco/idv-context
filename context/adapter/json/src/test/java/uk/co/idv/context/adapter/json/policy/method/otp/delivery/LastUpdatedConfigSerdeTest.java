package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfigModule;
import uk.co.idv.context.entities.policy.method.otp.delivery.LastUpdatedConfig;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class LastUpdatedConfigSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PhoneDeliveryMethodConfigModule());

    @ParameterizedTest(name = "should serialize last updated config {1}")
    @ArgumentsSource(LastUpdatedConfigArgumentsProvider.class)
    void shouldSerialize(String expectedJson, LastUpdatedConfig config) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(config);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize last updated config {1}")
    @ArgumentsSource(LastUpdatedConfigArgumentsProvider.class)
    void shouldDeserialize(String json, LastUpdatedConfig expectedConfig) throws JsonProcessingException {
        LastUpdatedConfig config = MAPPER.readValue(json, LastUpdatedConfig.class);

        assertThat(config).isEqualTo(expectedConfig);
    }

}
