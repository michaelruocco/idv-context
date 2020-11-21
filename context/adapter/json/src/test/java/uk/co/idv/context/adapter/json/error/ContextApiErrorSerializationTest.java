package uk.co.idv.context.adapter.json.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.common.adapter.json.error.ApiError;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class ContextApiErrorSerializationTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new ContextErrorModule());

    @ParameterizedTest(name = "should serialize context error {1}")
    @ArgumentsSource(ContextApiErrorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, ApiError error) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(error);

        assertThatJson(json).isEqualTo(expectedJson);
    }

}
