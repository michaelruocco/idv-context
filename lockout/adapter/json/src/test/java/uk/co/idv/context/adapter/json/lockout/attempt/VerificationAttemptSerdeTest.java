package uk.co.idv.context.adapter.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.alias.AliasModule;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.context.entities.lockout.attempt.VerificationAttemptMother;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class VerificationAttemptSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModules(new VerificationAttemptModule(), new AliasModule())
            .disable(WRITE_DATES_AS_TIMESTAMPS);

    private static final String JSON = VerificationAttemptJsonMother.build();
    private static final VerificationAttempt ATTEMPT = VerificationAttemptMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ATTEMPT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        VerificationAttempt attempt = MAPPER.readValue(JSON, VerificationAttempt.class);

        assertThat(attempt).isEqualTo(ATTEMPT);
    }

}
