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
        if (shouldError()) {
            throw new StubSimSwapExceptionErrorException(number.getValue());
        }
        if (shouldBeDelayed()) {
            delay.execute();
        }
        return buildEligibility();
    }

    private boolean shouldError() {
        return number.getLastDigit() == 4;
    }

    private boolean shouldBeDelayed() {
        return number.getLastDigit() == 5;
    }

    private Eligibility buildEligibility() {
        SimSwapResult result = resultFactory.build(number);
        return result.toEligibility(clock.instant());
    }

}
