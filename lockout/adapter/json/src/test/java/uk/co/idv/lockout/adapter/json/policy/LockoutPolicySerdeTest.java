package uk.co.idv.lockout.adapter.json.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.lockout.entities.policy.LockoutPolicy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new LockoutPolicyModule());

    @ParameterizedTest(name = "should serialize lockout policy {1}")
    @ArgumentsSource(LockoutPolicyArgumentsProvider.class)
    void shouldSerialize(String expectedJson, LockoutPolicy policy) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(policy);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize lockout policy {1}")
    @ArgumentsSource(LockoutPolicyArgumentsProvider.class)
    void shouldDeserialize(String json, LockoutPolicy expectedPolicy) throws JsonProcessingException {
        LockoutPolicy policy = MAPPER.readValue(json, LockoutPolicy.class);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

}
