package uk.co.idv.method.entities.otp.delivery.phone.voice;

import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.UUID;

public interface VoiceDeliveryMethodMother {

    static DeliveryMethod voice() {
        return builder().build();
    }

    static DeliveryMethod.DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("d22d7d89-9127-4a01-a99c-baaaf34124c7"))
                .type("voice")
                .value("+1604654321")
                .eligibility(EligibilityMother.eligible());
    }

}
