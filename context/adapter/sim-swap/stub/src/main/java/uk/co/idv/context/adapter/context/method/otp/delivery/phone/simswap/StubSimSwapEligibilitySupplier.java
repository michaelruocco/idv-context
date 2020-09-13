package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;

import java.time.Clock;
import java.util.function.Supplier;

@Builder
@Data
public class StubSimSwapEligibilitySupplier implements Supplier<Eligibility> {

    private final StubSimSwapResultFactory resultFactory;
    private final OtpPhoneNumber number;
    private final Clock clock;
    private final Delay delay;

    @Override
    public Eligibility get() {
        if (shouldBeDelayed()) {
            delay.execute();
        }
        SimSwapResult result = resultFactory.build(number);
        return result.toEligibility(clock.instant());
    }

    private boolean shouldBeDelayed() {
        return number.getLastDigit() == 5;
    }

}
