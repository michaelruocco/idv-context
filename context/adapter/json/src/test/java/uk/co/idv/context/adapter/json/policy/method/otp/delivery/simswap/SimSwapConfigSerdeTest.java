package uk.co.idv.context.adapter.json.policy.method.otp.delivery.simswap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap.SimSwapConfigModule;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class SimSwapConfigSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new SimSwapConfigModule());

    @ParameterizedTest(name = "should serialize sim swap config {1}")
    @ArgumentsSource(SimSwapConfigArgumentsProvider.class)
    void shouldSerialize(String expectedJson, SimSwapConfig config) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(config);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize sim swap config {1}")
    @ArgumentsSource(SimSwapConfigArgumentsProvider.class)
    void shouldDeserialize(String json, SimSwapConfig expectedConfig) throws JsonProcessingException {
        SimSwapConfig config = MAPPER.readValue(json, SimSwapConfig.class);

        assertThat(config).isEqualTo(expectedConfig);
    }

}
