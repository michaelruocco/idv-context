package uk.co.idv.method.entities.otp.delivery.query;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.sequence.MethodSequence;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Data
@RequiredArgsConstructor
public class GetDeliveryMethod implements Function<MethodSequence, Optional<DeliveryMethod>> {

    private final DeliveryMethodExtractor extractor;

    public GetDeliveryMethod(UUID deliveryMethodId) {
        this(new DeliveryMethodExtractor(deliveryMethodId));
    }

    @Override
    public Optional<DeliveryMethod> apply(MethodSequence sequence) {
        if (!sequence.isEligible()) {
            return Optional.empty();
        }
        return extractor.extractOptional(sequence);
    }

}
