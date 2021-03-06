package uk.co.idv.method.entities.otp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.query.DeliveryMethodNotFoundException;

import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

@Builder(toBuilder = true)
@Data
public class Otp implements Method {

    @Getter(AccessLevel.NONE)
    private final OtpConfig config;

    @Builder.Default
    private final DeliveryMethods deliveryMethods = new DeliveryMethods();

    @Override
    public String getName() {
        return OtpName.NAME;
    }

    @Override
    public Eligibility getEligibility() {
        return deliveryMethods.getEligibility();
    }

    @Override
    public OtpConfig getConfig() {
        return config;
    }

    public boolean containsDeliveryMethod(UUID id) {
        return findDeliveryMethod(id).isPresent();
    }

    public DeliveryMethod getDeliveryMethod(UUID id) {
        return findDeliveryMethod(id).orElseThrow(() -> new DeliveryMethodNotFoundException(id));
    }

    public boolean containsEligibleDeliveryMethod(UUID id) {
        return findDeliveryMethod(id)
                .map(DeliveryMethod::isEligible)
                .orElse(false);
    }

    public Optional<DeliveryMethod> findDeliveryMethod(UUID id) {
        return deliveryMethods.findByValue(id);
    }

    public Otp updateDeliveryMethods(UnaryOperator<DeliveryMethod> update) {
        return replaceDeliveryMethods(deliveryMethods.update(update));
    }

    public Otp replaceDeliveryMethods(DeliveryMethods updated) {
        return toBuilder()
                .deliveryMethods(deliveryMethods.replace(updated))
                .build();
    }

}
