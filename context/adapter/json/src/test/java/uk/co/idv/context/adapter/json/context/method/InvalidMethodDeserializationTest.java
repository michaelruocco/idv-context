package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidMethodDeserializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new MethodModule());
    private static final String JSON = InvalidMethodJsonMother.invalid();

    @Test
    void shouldThrowErrorOnDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, Method.class));

        assertThat(error)
                .isInstanceOf(InvalidMethodNameException.class)
                .hasMessage("invalid");
    }

}
