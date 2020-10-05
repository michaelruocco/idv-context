package uk.co.idv.context.usecases.context.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;
import uk.co.idv.method.entities.otp.delivery.phone.sms.SmsDeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.phone.voice.VoiceDeliveryMethodMother;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DeliveryMethodConfigsConverterTest {

    private final CompositeDeliveryMethodConfigConverter configConverter = mock(CompositeDeliveryMethodConfigConverter.class);

    private final DeliveryMethodConfigsConverter configsConverter = new DeliveryMethodConfigsConverter(configConverter);

    @Test
    void shouldReturnDeliveryMethodsFromAllConfigsCombined() {
        Identity identity = mock(Identity.class);
        DeliveryMethodConfig config1 = mock(DeliveryMethodConfig.class);
        DeliveryMethod expectedMethod1 = SmsDeliveryMethodMother.sms();
        given(configConverter.toDeliveryMethods(identity, config1)).willReturn(DeliveryMethodsMother.with(expectedMethod1));
        DeliveryMethodConfig config2 = mock(DeliveryMethodConfig.class);
        DeliveryMethod expectedMethod2 = VoiceDeliveryMethodMother.voice();
        given(configConverter.toDeliveryMethods(identity, config2)).willReturn(DeliveryMethodsMother.with(expectedMethod2));

        DeliveryMethods methods = configsConverter.toDeliveryMethods(identity, new DeliveryMethodConfigs(config1, config2));

        assertThat(methods).containsExactlyInAnyOrder(
                expectedMethod1,
                expectedMethod2
        );
    }

}
