package uk.co.idv.context.usecases.context.method.otp.delivery;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeliveryMethodConfigsConverter {

    private final CompositeDeliveryMethodConfigConverter configConverter;

    public DeliveryMethods toDeliveryMethods(Identity identity, DeliveryMethodConfigs configs) {
        return new DeliveryMethods(configs.stream()
                .map(config -> configConverter.toDeliveryMethods(identity, config))
                .flatMap(DeliveryMethods::stream)
                .collect(Collectors.toList()));
    }

}
