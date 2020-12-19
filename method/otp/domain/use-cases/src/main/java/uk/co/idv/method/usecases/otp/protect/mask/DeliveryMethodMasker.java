package uk.co.idv.method.usecases.otp.protect.mask;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.usecases.protect.mask.EmailAddressMasker;
import uk.co.idv.identity.usecases.protect.mask.PhoneNumberMasker;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class DeliveryMethodMasker implements UnaryOperator<DeliveryMethod> {

    private final Map<String, UnaryOperator<String>> maskers;

    public DeliveryMethodMasker() {
        this(buildMaskers());
    }

    @Override
    public DeliveryMethod apply(DeliveryMethod deliveryMethod) {
        UnaryOperator<String> masker = findMasker(deliveryMethod.getType());
        String masked = masker.apply(deliveryMethod.getValue());
        return deliveryMethod.withValue(masked);
    }

    private UnaryOperator<String> findMasker(String type) {
        return Optional.ofNullable(maskers.get(type))
                .orElseThrow(() -> new DeliveryMethodMaskingNotSupportedException(type));
    }

    private static Map<String, UnaryOperator<String>> buildMaskers() {
        return Map.of(
                "email", new EmailAddressMasker(),
                "sms", new PhoneNumberMasker(),
                "voice", new PhoneNumberMasker()
        );
    }

}
