package uk.co.idv.context.entities.context.method.otp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.result.Result;
import uk.co.idv.context.entities.context.result.Results;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;

import java.util.Optional;
import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class Otp implements Method {

    private final String name;

    @Getter(AccessLevel.NONE)
    private final OtpConfig otpConfig;

    @Builder.Default
    private final Results results = new Results();

    @Builder.Default
    private final DeliveryMethods deliveryMethods = new DeliveryMethods();

    @Override
    public Eligibility getEligibility() {
        return deliveryMethods.getEligibility();
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

    @Override
    public OtpConfig getConfig() {
        return otpConfig;
    }

    public Optional<DeliveryMethod> findDeliveryMethod(UUID id) {
        return deliveryMethods.findByValue(id);
    }

    public Otp add(Result result) {
        return toBuilder()
                .results(results.add(result))
                .build();
    }

    public Otp replaceDeliveryMethods(DeliveryMethods updated) {
        return toBuilder()
                .deliveryMethods(deliveryMethods.replace(updated))
                .build();
    }

}
