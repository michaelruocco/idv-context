package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.lockout.entities.ClockMother;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidIncludeAttemptsPolicyDeserializationTest {

    private static final Clock CLOCK = ClockMother.build();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new IncludeAttemptsPolicyModule(CLOCK));

    @Test
    void shouldThrowExceptionIfInvalidIncludeAttemptsPolicyDeserialized() throws JsonProcessingException {
        String json = InvalidIncludeAttemptsPolicyJsonMother.json();

        Throwable error = catchThrowable(() -> MAPPER.readValue(json, IncludeAttemptsPolicy.class));

        assertThat(error)
                .isInstanceOf(InvalidIncludeAttemptPolicyTypeException.class)
                .hasMessage("invalid");
    }

}
