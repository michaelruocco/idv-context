package uk.co.idv.lockout.adapter.json.attempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.alias.AliasModule;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.attempt.AttemptMother;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class AttemptSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModules(new AttemptModule(), new AliasModule())
            .disable(WRITE_DATES_AS_TIMESTAMPS);

    private static final String JSON = VerificationAttemptJsonMother.build();
    private static final Attempt ATTEMPT = AttemptMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ATTEMPT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Attempt attempt = MAPPER.readValue(JSON, Attempt.class);

        assertThat(attempt).isEqualTo(ATTEMPT);
    }

}
