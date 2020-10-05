package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfigModule;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OtpPhoneNumberConfigSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PhoneDeliveryMethodConfigModule());

    @ParameterizedTest(name = "should serialize otp phone number config {1}")
    @ArgumentsSource(OtpPhoneNumberConfigArgumentsProvider.class)
    void shouldSerialize(String expectedJson, OtpPhoneNumberConfig config) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(config);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize otp phone number config {1}")
    @ArgumentsSource(OtpPhoneNumberConfigArgumentsProvider.class)
    void shouldDeserialize(String json, OtpPhoneNumberConfig expectedConfig) throws JsonProcessingException {
        OtpPhoneNumberConfig config = MAPPER.readValue(json, OtpPhoneNumberConfig.class);

        assertThat(config).isEqualTo(expectedConfig);
    }

}
