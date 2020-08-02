package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.recordattempt.NeverRecordAttemptPolicy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class NeverRecordAttemptPolicySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new RecordAttemptPolicyModule());
    private static final RecordAttemptPolicy POLICY = new NeverRecordAttemptPolicy();
    private static final String JSON = NeverRecordAttemptPolicyJsonMother.json();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        RecordAttemptPolicy policy = MAPPER.readValue(JSON, RecordAttemptPolicy.class);

        assertThat(policy).isEqualTo(POLICY);
    }

}
