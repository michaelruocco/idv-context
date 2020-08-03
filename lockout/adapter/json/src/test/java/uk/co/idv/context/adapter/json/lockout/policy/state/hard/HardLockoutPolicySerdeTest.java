package uk.co.idv.context.adapter.json.lockout.policy.state.hard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.lockout.policy.LockoutPolicyModule;
import uk.co.idv.context.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutPolicyMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class HardLockoutPolicySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutPolicyModule());
    private static final String JSON = HardLockoutPolicyJsonMother.build();
    private static final LockoutPolicy POLICY = HardLockoutPolicyMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        LockoutPolicy policy = MAPPER.readValue(JSON, LockoutPolicy.class);

        assertThat(policy).isEqualTo(POLICY);
    }

}
