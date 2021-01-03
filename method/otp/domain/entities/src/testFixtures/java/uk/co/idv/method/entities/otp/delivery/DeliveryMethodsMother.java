package uk.co.idv.method.entities.otp.delivery;

import uk.co.idv.method.entities.otp.delivery.email.EmailDeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.phone.sms.SmsDeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.phone.voice.VoiceDeliveryMethodMother;

import java.util.Collections;

public interface DeliveryMethodsMother {

    static DeliveryMethods oneOfEach() {
        return with(
                SmsDeliveryMethodMother.sms(),
                VoiceDeliveryMethodMother.voice(),
                EmailDeliveryMethodMother.email()
        );
    }

    static DeliveryMethods empty() {
        return new DeliveryMethods(Collections.emptyList());
    }

    static DeliveryMethods with(DeliveryMethod... methods) {
        return new DeliveryMethods(methods);
    }

}
