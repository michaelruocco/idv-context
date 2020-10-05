package uk.co.idv.method.entities.otp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.Results;

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
