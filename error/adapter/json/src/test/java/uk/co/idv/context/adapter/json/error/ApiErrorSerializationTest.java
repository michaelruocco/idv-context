package uk.co.idv.context.adapter.json.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ApiErrorSerializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new ErrorModule());

    @ParameterizedTest(name = "should serialize error {1}")
    @ArgumentsSource(ErrorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, ApiError error) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(error);

        assertThatJson(json).isEqualTo(expectedJson);
    }

}
