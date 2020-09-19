package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.MethodPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidMethodPolicyDeserializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new MethodPolicyModule());
    private static final String JSON = InvalidMethodPolicyJsonMother.invalid();

    @Test
    void shouldThrowErrorOnDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, MethodPolicy.class));

        assertThat(error)
                .isInstanceOf(InvalidMethodPolicyNameException.class)
                .hasMessage("invalid");
    }

}
