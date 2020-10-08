package uk.co.idv.method.entities.otp.delivery;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.MethodToType;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public class DeliveryMethodEligible implements Predicate<MethodSequence> {

    private final UUID deliveryMethodId;

    @Override
    public boolean test(MethodSequence sequence) {
        if (!sequence.isEligible()) {
            return false;
        }
        return sequence.getNext("one-time-passcode")
                .map(new MethodToType<>(Otp.class))
                .map(Optional::get)
                .map(otp -> otp.findDeliveryMethod(deliveryMethodId))
                .map(Optional::get)
                .filter(DeliveryMethod::isEligibilityComplete)
                .filter(DeliveryMethod::isEligible)
                .isPresent();
    }

}
