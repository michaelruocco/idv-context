package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class MethodsSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new MethodModule());
    private static final Methods METHODS = MethodsMother.otpOnly();
    private static final String JSON = MethodsJsonMother.otpOnly();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(METHODS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Methods methods = MAPPER.readValue(JSON, Methods.class);

        assertThat(methods).isEqualTo(METHODS);
    }

}
