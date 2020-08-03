package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class LockoutStateCalculatorSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutStateCalculatorModule());

    @ParameterizedTest(name = "should serialize state calculator {1}")
    @ArgumentsSource(LockoutStateCalculatorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, LockoutStateCalculator stateCalculator) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(stateCalculator);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize state calculator {1}")
    @ArgumentsSource(LockoutStateCalculatorArgumentsProvider.class)
    void shouldDeserialize(String json, LockoutStateCalculator expectedStateCalculator) throws JsonProcessingException {
        LockoutStateCalculator calculator = MAPPER.readValue(json, LockoutStateCalculator.class);

        assertThat(calculator).isEqualTo(expectedStateCalculator);
    }

}
