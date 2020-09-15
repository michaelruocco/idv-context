package uk.co.idv.context.entities.policy.method.otp.delivery;

import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfigMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfigMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.voice.VoiceDeliveryMethodConfigMother;

public interface DeliveryMethodConfigsMother {

    static DeliveryMethodConfigs oneOfEach() {
        return with(
                SmsDeliveryMethodConfigMother.build(),
                VoiceDeliveryMethodConfigMother.build(),
                EmailDeliveryMethodConfigMother.build()
        );
    }

    static DeliveryMethodConfigs with(DeliveryMethodConfig... configs) {
        return new DeliveryMethodConfigs(configs);
    }

}
