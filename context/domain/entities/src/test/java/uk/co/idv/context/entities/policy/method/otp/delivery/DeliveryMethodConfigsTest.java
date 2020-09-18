package uk.co.idv.context.entities.policy.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfigMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfigMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.voice.VoiceDeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.voice.VoiceDeliveryMethodConfigMother;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;

import java.time.Duration;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodConfigsTest {

    @Test
    void shouldBeIterable() {
        EmailDeliveryMethodConfig emailConfig = EmailDeliveryMethodConfigMother.email();
        SmsDeliveryMethodConfig smsConfig = SmsDeliveryMethodConfigMother.sms();

        DeliveryMethodConfigs configs = new DeliveryMethodConfigs(emailConfig, smsConfig);

        assertThat(configs).containsExactly(emailConfig, smsConfig);
    }

    @Test
    void shouldReturnStream() {
        EmailDeliveryMethodConfig emailConfig = EmailDeliveryMethodConfigMother.email();
        SmsDeliveryMethodConfig smsConfig = SmsDeliveryMethodConfigMother.sms();
        DeliveryMethodConfigs configs = new DeliveryMethodConfigs(emailConfig, smsConfig);

        Stream<DeliveryMethodConfig> stream = configs.stream();

        assertThat(stream).containsExactly(emailConfig, smsConfig);
    }

    @Test
    void shouldReturnRequestedDataFromAllConfigsUniquely() {
        String item1 = "item-1";
        String item2 = "item-2";
        DeliveryMethodConfig config1 = givenConfigWithRequestedData(item1);
        DeliveryMethodConfig config2 = givenConfigWithRequestedData(item2);
        DeliveryMethodConfig config3 = givenConfigWithRequestedData(item2);
        DeliveryMethodConfigs configs = new DeliveryMethodConfigs(config1, config2, config3);

        RequestedData requestedData = configs.getRequestedData();

        assertThat(requestedData).containsExactly(item1, item2);
    }

    @Test
    void shouldReturnEmptySimSwapTimeoutIfNoneConfigured() {
        EmailDeliveryMethodConfig emailConfig = EmailDeliveryMethodConfigMother.email();
        DeliveryMethodConfigs configs = new DeliveryMethodConfigs(emailConfig);

        Optional<Duration> timeout = configs.getLongestSimSwapConfigTimeout();

        assertThat(timeout).isEmpty();
    }

    @Test
    void shouldReturnLongestSimSwapTimeoutIfMoreThanOneConfigured() {
        Duration longestTimeout = Duration.ofSeconds(5);
        Duration shortestTimeout = Duration.ofSeconds(2);
        SmsDeliveryMethodConfig smsConfig = SmsDeliveryMethodConfigMother.withSimSwapTimeout(longestTimeout);
        VoiceDeliveryMethodConfig voiceConfig = VoiceDeliveryMethodConfigMother.withSimSwapTimeout(shortestTimeout);
        EmailDeliveryMethodConfig emailConfig = EmailDeliveryMethodConfigMother.email();
        DeliveryMethodConfigs configs = new DeliveryMethodConfigs(smsConfig, voiceConfig, emailConfig);

        Optional<Duration> timeout = configs.getLongestSimSwapConfigTimeout();

        assertThat(timeout).contains(longestTimeout);
    }

    private DeliveryMethodConfig givenConfigWithRequestedData(String item) {
        DeliveryMethodConfig config = mock(DeliveryMethodConfig.class);
        given(config.getRequestedData()).willReturn(RequestedDataMother.with(item));
        return config;
    }

}
