package uk.co.idv.context.entities.context.method.otp.delivery;

import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod.DeliveryMethodBuilder;

import java.util.UUID;

public interface VoiceDeliveryMethodMother {

    static DeliveryMethod voice() {
        return builder().build();
    }

    static DeliveryMethodBuilder builder() {
        return DeliveryMethod.builder()
                .id(UUID.fromString("d22d7d89-9127-4a01-a99c-baaaf34124c7"))
                .type("voice")
                .value("+1604654321")
                .eligibility(new Eligible());
    }

}
