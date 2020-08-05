package uk.co.idv.context.adapter.json.policy.key;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.policy.PolicyKey;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PolicyKeySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PolicyKeyModule());

    @ParameterizedTest(name = "should serialize policy key {1}")
    @ArgumentsSource(PolicyKeyArgumentsProvider.class)
    void shouldSerialize(String expectedJson, PolicyKey key) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(key);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize policy key {1}")
    @ArgumentsSource(PolicyKeyArgumentsProvider.class)
    void shouldDeserialize(String json, PolicyKey expectedKey) throws JsonProcessingException {
        PolicyKey key = MAPPER.readValue(json, PolicyKey.class);

        assertThat(key).isEqualTo(expectedKey);
    }

}
