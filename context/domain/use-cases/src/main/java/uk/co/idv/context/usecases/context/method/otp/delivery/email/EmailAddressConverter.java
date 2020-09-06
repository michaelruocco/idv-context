package uk.co.idv.context.usecases.context.method.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;


@RequiredArgsConstructor
public class EmailAddressConverter {

    private final IdGenerator idGenerator;

    public DeliveryMethod toDeliveryMethod(String emailAddress, DeliveryMethodConfig config) {
        return DeliveryMethod.builder()
                .id(idGenerator.generate())
                .type(config.getType())
                .value(emailAddress)
                .eligibility(new Eligible())
                .build();
    }

}
