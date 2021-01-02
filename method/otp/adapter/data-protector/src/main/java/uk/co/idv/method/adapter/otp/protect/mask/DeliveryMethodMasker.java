package uk.co.idv.method.adapter.otp.protect.mask;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.adapter.protect.mask.emailaddress.EmailAddressStringMasker;
import uk.co.idv.identity.adapter.protect.mask.phonenumber.PhoneNumberStringMasker;
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
        UnaryOperator<String> phoneNumberMasker = new PhoneNumberStringMasker();
        return Map.of(
                "email", new EmailAddressStringMasker(),
                "sms", phoneNumberMasker,
                "voice", phoneNumberMasker
        );
    }

}
