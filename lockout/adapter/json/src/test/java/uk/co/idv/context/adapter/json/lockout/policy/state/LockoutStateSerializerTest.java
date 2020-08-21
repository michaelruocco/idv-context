package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.lockout.policy.LockoutState;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutStateSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new LockoutStateModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @ParameterizedTest(name = "should serialize lockout state {1}")
    @ArgumentsSource(LockoutStateArgumentsProvider.class)
    void shouldSerialize(String expectedJson, LockoutState state) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(state);

        assertThatJson(json).isEqualTo(expectedJson);
    }

}
