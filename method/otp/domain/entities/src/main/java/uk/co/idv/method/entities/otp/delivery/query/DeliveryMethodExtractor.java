package uk.co.idv.method.entities.otp.delivery.query;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.MethodToType;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DeliveryMethodExtractor {

    private final UUID deliveryMethodId;

    public DeliveryMethod extract(MethodSequence sequence) {
        return extractOptional(sequence)
                .orElseThrow(() -> new DeliveryMethodNotFoundException(deliveryMethodId));
    }

    public Optional<DeliveryMethod> extractOptional(MethodSequence sequence) {
        return sequence.getNext()
                .flatMap(new MethodToType<>(Otp.class))
                .flatMap(otp -> otp.findDeliveryMethod(deliveryMethodId));
    }

}
