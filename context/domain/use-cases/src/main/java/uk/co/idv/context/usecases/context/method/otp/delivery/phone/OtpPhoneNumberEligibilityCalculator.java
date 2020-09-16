package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import lombok.Builder;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.time.Clock;

@Builder
public class OtpPhoneNumberEligibilityCalculator {

    @Builder.Default
    private final Clock clock = Clock.systemUTC();

    private final SimSwapExecutor simSwapExecutor;

    public Eligibility toEligibility(OtpPhoneNumber number, PhoneDeliveryMethodConfig config) {
        Eligibility eligibility = config.toEligibility(number, clock.instant());
        if (!eligibility.isEligible()) {
            return eligibility;
        }
        if (!number.isMobile()) {
            return eligibility;
        }
        return config.getSimSwapConfig()
                .map(simSwapConfig -> toEligibility(number, simSwapConfig))
                .orElse(eligibility);
    }


    private Eligibility toEligibility(OtpPhoneNumber number, SimSwapConfig config) {
        return simSwapExecutor.executeSimSwap(number, config);
    }

}
