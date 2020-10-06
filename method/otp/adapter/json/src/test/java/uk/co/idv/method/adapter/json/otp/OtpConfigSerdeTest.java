package uk.co.idv.method.adapter.json.otp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.adapter.json.otp.policy.OtpPolicyModule;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.OtpConfigMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OtpConfigSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new OtpPolicyModule());
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
