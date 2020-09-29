package uk.co.idv.context.entities.context.method.otp.delivery;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.NoEligibleDeliveryMethodsAvailable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class DeliveryMethods implements Iterable<DeliveryMethod> {

    private final Collection<DeliveryMethod> values;

    public DeliveryMethods(DeliveryMethod... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<DeliveryMethod> iterator() {
        return values.iterator();
    }

    public Stream<DeliveryMethod> stream() {
        return values.stream();
    }

    public EligibilityFutures toSimSwapFutures() {
        return new EligibilityFutures(values.stream()
                .map(DeliveryMethod::getAsyncSimSwapEligibilityFuture)
                .flatMap(Optional::stream)
                .collect(Collectors.toList())
        );
    }

    public Eligibility getEligibility() {
        if (hasAtLeastOneEligible()) {
            return new Eligible();
        }
        return new NoEligibleDeliveryMethodsAvailable();
    }

    public DeliveryMethods replace(DeliveryMethods newValues) {
        Map<UUID, DeliveryMethod> updated = asMap();
        newValues.stream()
                .filter(method -> updated.containsKey(method.getId()))
                .forEach(method -> updated.put(method.getId(), method));
        return new DeliveryMethods(updated.values());
    }

    private Map<UUID, DeliveryMethod> asMap() {
        return values.stream().collect(Collectors.toMap(DeliveryMethod::getId, Function.identity()));
    }

    private boolean hasAtLeastOneEligible() {
        return values.stream().anyMatch(DeliveryMethod::isEligible);
    }

}
