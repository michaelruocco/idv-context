package uk.co.idv.lockout.adapter.json.policy.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.lockout.entities.policy.LockoutState;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutStateSerializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new LockoutStateModule());

    @ParameterizedTest(name = "should serialize lockout state {1}")
    @ArgumentsSource(LockoutStateArgumentsProvider.class)
    void shouldSerialize(String expectedJson, LockoutState state) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(state);

        assertThatJson(json).isEqualTo(expectedJson);
    }

}
