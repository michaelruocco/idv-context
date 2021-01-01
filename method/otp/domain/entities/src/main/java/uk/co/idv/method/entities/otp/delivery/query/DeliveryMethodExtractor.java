package uk.co.idv.method.entities.otp.delivery.query;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.MethodToType;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DeliveryMethodExtractor {

    public DeliveryMethod extract(Context context, UUID deliveryMethodId) {
        return extractOptional(context, deliveryMethodId)
                .orElseThrow(() -> new DeliveryMethodNotFoundException(deliveryMethodId));
    }

    public Optional<DeliveryMethod> extractOptional(Context context, UUID deliveryMethodId) {
        return extractOtpMethods(context)
                .map(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Stream<Otp> extractOtpMethods(Context context) {
        Methods methods = context.getNextMethods("one-time-passcode");
        return methods.stream().map(new MethodToType<>(Otp.class)).flatMap(Optional::stream);
    }

}
