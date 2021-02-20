package uk.co.idv.method.usecases.otp.delivery.email;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.mruoc.randomvalue.uuid.RandomUuidGenerator;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;


@RequiredArgsConstructor
public class EmailAddressConverter {

    private final UuidGenerator uuidGenerator;

    public EmailAddressConverter() {
        this(new RandomUuidGenerator());
    }

    public DeliveryMethod toDeliveryMethod(EmailAddress emailAddress, DeliveryMethodConfig config) {
        return DeliveryMethod.builder()
                .id(uuidGenerator.generate())
                .type(config.getType())
                .value(emailAddress.getValue())
                .eligibility(new Eligible())
                .build();
    }

}
