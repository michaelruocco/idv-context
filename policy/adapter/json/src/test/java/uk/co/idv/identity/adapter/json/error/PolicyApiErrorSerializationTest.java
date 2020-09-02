package uk.co.idv.identity.adapter.json.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PolicyApiErrorSerializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PolicyErrorModule());

    @ParameterizedTest(name = "should serialize policy error {1}")
    @ArgumentsSource(PolicyApiErrorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, ApiError error) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(error);

        assertThatJson(json).isEqualTo(expectedJson);
    }

}
