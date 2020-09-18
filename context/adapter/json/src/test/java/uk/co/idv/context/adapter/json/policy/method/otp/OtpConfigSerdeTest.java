package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;
import uk.co.idv.context.entities.policy.method.otp.OtpConfigMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class OtpConfigSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new OtpPolicyModule());
    private static final OtpConfig CONFIG = OtpConfigMother.build();
    private static final String JSON = OtpConfigJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(CONFIG);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        OtpConfig config = MAPPER.readValue(JSON, OtpConfig.class);

        assertThat(config).isEqualTo(CONFIG);
    }

}
