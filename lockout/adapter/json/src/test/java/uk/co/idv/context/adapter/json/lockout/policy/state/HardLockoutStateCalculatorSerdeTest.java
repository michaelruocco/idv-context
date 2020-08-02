package uk.co.idv.context.adapter.json.lockout.policy.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.LockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateCalculator;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class HardLockoutStateCalculatorSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutStateCalculatorModule());
    private static final String JSON = HardLockoutStateCalculatorJsonMother.build();
    private static final LockoutStateCalculator CALCULATOR = new HardLockoutStateCalculator(3);

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(CALCULATOR);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        LockoutStateCalculator calculator = MAPPER.readValue(JSON, LockoutStateCalculator.class);

        assertThat(calculator).isEqualTo(CALCULATOR);
    }

}
