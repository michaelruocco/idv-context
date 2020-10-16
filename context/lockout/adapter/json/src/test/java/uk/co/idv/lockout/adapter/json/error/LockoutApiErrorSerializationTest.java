package uk.co.idv.lockout.adapter.json.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.error.ApiError;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutApiErrorSerializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new LockoutErrorModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @ParameterizedTest(name = "should serialize identity error {1}")
    @ArgumentsSource(LockoutApiErrorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, ApiError error) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(error);

        assertThatJson(json).isEqualTo(expectedJson);
    }

}
