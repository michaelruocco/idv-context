package uk.co.idv.method.entities.otp.delivery.query;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.UUID;
import java.util.function.Predicate;

@Data
@RequiredArgsConstructor
public class DeliveryMethodEligible implements Predicate<MethodSequence> {

    private final DeliveryMethodExtractor extractor;

    public DeliveryMethodEligible(UUID deliveryMethodId) {
        this(new DeliveryMethodExtractor(deliveryMethodId));
    }

    @Override
    public boolean test(MethodSequence sequence) {
        if (!sequence.isEligible()) {
            return false;
        }
        DeliveryMethod deliveryMethod = extractor.extract(sequence);
        return deliveryMethod.isEligibilityComplete() && deliveryMethod.isEligible();
    }

}
