package uk.co.idv.method.adapter.json.otp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.adapter.json.otp.policy.OtpPolicyModule;
import uk.co.idv.method.entities.otp.PasscodeConfig;
import uk.co.idv.method.entities.otp.PasscodeConfigMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PasscodeConfigSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new OtpPolicyModule());
    private static final PasscodeConfig CONFIG = PasscodeConfigMother.build();
    private static final String JSON = PasscodeConfigJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(CONFIG);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        PasscodeConfig config = MAPPER.readValue(JSON, PasscodeConfig.class);

        assertThat(config).isEqualTo(CONFIG);
    }

}
