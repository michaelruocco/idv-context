package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.context.method.Method;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class MethodSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .registerModule(new MethodModule());

    @ParameterizedTest(name = "should serialize method {1}")
    @ArgumentsSource(MethodArgumentsProvider.class)
    void shouldSerialize(String expectedJson, Method method) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(method);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize method {1}")
    @ArgumentsSource(MethodArgumentsProvider.class)
    void shouldDeserialize(String json, Method expectedMethod) throws JsonProcessingException {
        Method method = MAPPER.readValue(json, Method.class);

        assertThat(method).isEqualTo(expectedMethod);
    }

}