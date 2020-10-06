package uk.co.idv.method.adapter.json.otp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OtpSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new OtpModule());
    private static final Otp OTP = OtpMother.build();
    private static final String JSON = OtpJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(OTP);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Otp otp = MAPPER.readValue(JSON, Otp.class);

        assertThat(otp).isEqualTo(OTP);
    }

}
