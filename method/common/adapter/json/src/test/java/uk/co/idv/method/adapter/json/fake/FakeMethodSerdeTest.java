package uk.co.idv.method.adapter.json.fake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.entities.method.fake.FakeMethod;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class FakeMethodSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new FakeMethodModule());

    @ParameterizedTest(name = "should serialize fake method {1}")
    @ArgumentsSource(FakeMethodArgumentsProvider.class)
    void shouldSerialize(String expectedJson, FakeMethod method) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(method);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize fake method {1}")
    @ArgumentsSource(FakeMethodArgumentsProvider.class)
    void shouldDeserialize(String json, FakeMethod expectedMethod) throws JsonProcessingException {
        FakeMethod method = MAPPER.readValue(json, FakeMethod.class);

        assertThat(method).isEqualTo(expectedMethod);
    }

}
