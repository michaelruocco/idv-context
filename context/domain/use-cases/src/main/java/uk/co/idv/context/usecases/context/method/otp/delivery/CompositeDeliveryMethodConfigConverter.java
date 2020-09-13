package uk.co.idv.context.usecases.context.method.otp.delivery;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class CompositeDeliveryMethodConfigConverter {

    private final Collection<DeliveryMethodConfigConverter> converters;

    public CompositeDeliveryMethodConfigConverter(DeliveryMethodConfigConverter... converters) {
        this(Arrays.asList(converters));
    }

    public DeliveryMethods toDeliveryMethods(Identity identity, DeliveryMethodConfig config) {
        return findConverter(config)
                .map(converter -> converter.toDeliveryMethods(identity, config))
                .orElseThrow(() -> new DeliveryMethodConfigNotSupportedException(config.getType()));
    }

    private Optional<DeliveryMethodConfigConverter> findConverter(DeliveryMethodConfig config) {
        return converters.stream().filter(converter -> converter.supports(config)).findFirst();
    }

}
