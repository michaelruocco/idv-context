package uk.co.idv.method.entities.otp.policy.delivery;

import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfigMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.sms.SmsDeliveryMethodConfigMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.voice.VoiceDeliveryMethodConfigMother;

public interface DeliveryMethodConfigsMother {

    static DeliveryMethodConfigs oneOfEach() {
        return with(
                SmsDeliveryMethodConfigMother.sms(),
                VoiceDeliveryMethodConfigMother.voice(),
                EmailDeliveryMethodConfigMother.email()
        );
    }

    static DeliveryMethodConfigs with(DeliveryMethodConfig... configs) {
        return new DeliveryMethodConfigs(configs);
    }

}
