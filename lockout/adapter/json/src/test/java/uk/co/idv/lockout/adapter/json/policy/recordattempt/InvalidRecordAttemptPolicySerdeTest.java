package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidRecordAttemptPolicySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new RecordAttemptPolicyModule());
    private static final String JSON = InvalidRecordAttemptPolicyJsonMother.invalid();

    @Test
    void shouldDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, RecordAttemptPolicy.class));

        assertThat(error)
                .isInstanceOf(InvalidRecordAttemptPolicyTypeException.class)
                .hasMessage("invalid");
    }

}
