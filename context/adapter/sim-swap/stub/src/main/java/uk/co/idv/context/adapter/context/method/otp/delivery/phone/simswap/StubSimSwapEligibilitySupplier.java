package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.common.usecases.duration.DurationCalculator;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Clock;
import java.time.Instant;
import java.util.function.Supplier;

@Builder
@Data
@Slf4j
public class StubSimSwapEligibilitySupplier implements Supplier<Eligibility> {

    private final StubSimSwapResultFactory resultFactory;
    private final SimSwapConfig config;
    private final OtpPhoneNumber number;
    private final Clock clock;
    private final Delay delay;

    @Override
    public Eligibility get() {
        Instant start = Instant.now();
        try {
            return perform();
        } finally {
            log.debug("sim swap eligibility supplier for {} took {}ms", number, DurationCalculator.millisBetweenNowAnd(start));
        }
    }

    private Eligibility perform() {
        log.debug("running stub sim swap eligibility supplier for {}", number);
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
        SimSwapResult result = resultFactory.build(number, config);
        return result.toEligibility(clock.instant());
    }

}
