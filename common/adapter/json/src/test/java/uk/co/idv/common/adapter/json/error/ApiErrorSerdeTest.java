package uk.co.idv.common.adapter.json.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ApiErrorSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new ErrorModule());

    @ParameterizedTest(name = "should serialize error {1}")
    @ArgumentsSource(ErrorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, ApiError error) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(error);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize error {1}")
    @ArgumentsSource(ErrorArgumentsProvider.class)
    void shouldDeserialize(String json, ApiError expectedError) throws JsonProcessingException {
        ApiError error = MAPPER.readValue(json, ApiError.class);

        assertThat(error).usingRecursiveComparison().isEqualTo(expectedError);
    }

}
