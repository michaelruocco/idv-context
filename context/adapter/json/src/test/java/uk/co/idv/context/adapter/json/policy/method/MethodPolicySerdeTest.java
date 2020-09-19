package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.policy.method.MethodPolicy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class MethodPolicySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new MethodPolicyModule());

    @ParameterizedTest(name = "should serialize method policy {1}")
    @ArgumentsSource(MethodPolicyArgumentsProvider.class)
    void shouldSerialize(String expectedJson, MethodPolicy policy) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(policy);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize method policy {1}")
    @ArgumentsSource(MethodPolicyArgumentsProvider.class)
    void shouldDeserialize(String json, MethodPolicy expectedPolicy) throws JsonProcessingException {
        MethodPolicy config = MAPPER.readValue(json, MethodPolicy.class);

        assertThat(config).isEqualTo(expectedPolicy);
    }

}
