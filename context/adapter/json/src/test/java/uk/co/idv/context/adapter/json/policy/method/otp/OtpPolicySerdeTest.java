package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.method.MethodMapping;
import uk.co.idv.context.adapter.json.policy.method.MethodPolicyModule;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OtpPolicySerdeTest {

    private static final MethodMapping MAPPING = new OtpMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new MethodPolicyModule(MAPPING));
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
