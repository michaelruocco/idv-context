package uk.co.idv.lockout.adapter.json.policy.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.lockout.entities.ClockMother;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;

import java.time.Clock;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class LockoutStateCalculatorSerdeTest {

    private static final Clock CLOCK = ClockMother.build();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new LockoutStateCalculatorModule(CLOCK));

    @ParameterizedTest(name = "should serialize lockout state calculator {1}")
    @ArgumentsSource(LockoutStateCalculatorArgumentsProvider.class)
    void shouldSerialize(String expectedJson, LockoutStateCalculator stateCalculator) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(stateCalculator);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize lockout state calculator {1}")
    @ArgumentsSource(LockoutStateCalculatorArgumentsProvider.class)
    void shouldDeserialize(String json, LockoutStateCalculator expectedStateCalculator) throws JsonProcessingException {
        LockoutStateCalculator calculator = MAPPER.readValue(json, LockoutStateCalculator.class);

        assertThat(calculator).isEqualTo(expectedStateCalculator);
    }

}
