package uk.co.idv.context.usecases.context.method.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.common.usecases.id.RandomIdGenerator;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;


@RequiredArgsConstructor
public class EmailAddressConverter {

    private final IdGenerator idGenerator;

    public EmailAddressConverter() {
        this(new RandomIdGenerator());
    }

    public DeliveryMethod toDeliveryMethod(String emailAddress, DeliveryMethodConfig config) {
        return DeliveryMethod.builder()
                .id(idGenerator.generate())
                .type(config.getType())
                .value(emailAddress)
                .eligibility(new Eligible())
                .build();
    }

}
