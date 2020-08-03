package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class RecordAttemptPolicySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new RecordAttemptPolicyModule());

    @ParameterizedTest(name = "should serialize record attempts policy {1}")
    @ArgumentsSource(RecordAttemptPolicyArgumentsProvider.class)
    void shouldSerialize(String expectedJson, RecordAttemptPolicy policy) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(policy);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize record attempts policy {1}")
    @ArgumentsSource(RecordAttemptPolicyArgumentsProvider.class)
    void shouldDeserialize(String json, RecordAttemptPolicy expectedPolicy) throws JsonProcessingException {
        RecordAttemptPolicy policy = MAPPER.readValue(json, RecordAttemptPolicy.class);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

}
