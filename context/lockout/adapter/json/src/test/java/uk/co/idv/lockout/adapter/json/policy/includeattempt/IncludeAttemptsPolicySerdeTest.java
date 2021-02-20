package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.lockout.entities.ClockMother;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsPolicy;

import java.time.Clock;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class IncludeAttemptsPolicySerdeTest {

    private static final Clock CLOCK = ClockMother.build();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new IncludeAttemptsPolicyModule(CLOCK));

    @ParameterizedTest(name = "should serialize include attempts policy {1}")
    @ArgumentsSource(IncludeAttemptsPolicyArgumentsProvider.class)
    void shouldSerialize(String expectedJson, IncludeAttemptsPolicy policy) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(policy);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize include attempts policy {1}")
    @ArgumentsSource(IncludeAttemptsPolicyArgumentsProvider.class)
    void shouldDeserialize(String json, IncludeAttemptsPolicy expectedPolicy) throws JsonProcessingException {
        IncludeAttemptsPolicy policy = MAPPER.readValue(json, IncludeAttemptsPolicy.class);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

}
