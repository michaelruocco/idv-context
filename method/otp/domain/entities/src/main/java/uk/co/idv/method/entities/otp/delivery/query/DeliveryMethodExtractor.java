package uk.co.idv.method.entities.otp.delivery.query;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodExtractor;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DeliveryMethodExtractor {

    public DeliveryMethod extract(Iterable<Method> methods, UUID deliveryMethodId) {
        return extractOptional(methods, deliveryMethodId)
                .orElseThrow(() -> new DeliveryMethodNotFoundException(deliveryMethodId));
    }

    public Optional<DeliveryMethod> extractOptional(Iterable<Method> methods, UUID deliveryMethodId) {
        return MethodExtractor.extractByType(methods, Otp.class)
                .map(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .flatMap(Optional::stream)
                .findFirst();
    }

}
