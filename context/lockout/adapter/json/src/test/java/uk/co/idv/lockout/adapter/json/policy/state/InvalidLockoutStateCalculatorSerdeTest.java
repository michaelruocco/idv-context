package uk.co.idv.lockout.adapter.json.policy.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.policy.LockoutStateCalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidLockoutStateCalculatorSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutStateCalculatorModule());
    private static final String JSON = InvalidLockoutStateCalculatorJsonMother.invalid();

    @Test
    void shouldDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, LockoutStateCalculator.class));

        assertThat(error)
                .isInstanceOf(InvalidLockoutStateCalculatorTypeException.class)
                .hasMessage("invalid");
    }

}
