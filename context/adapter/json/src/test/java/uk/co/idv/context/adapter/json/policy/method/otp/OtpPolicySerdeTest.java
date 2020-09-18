package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicyMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OtpPolicySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new OtpPolicyModule());
    private static final OtpPolicy POLICY = OtpPolicyMother.build();
    private static final String JSON = OtpPolicyJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        OtpPolicy policy = MAPPER.readValue(JSON, OtpPolicy.class);

        assertThat(policy).isEqualTo(POLICY);
    }

}
