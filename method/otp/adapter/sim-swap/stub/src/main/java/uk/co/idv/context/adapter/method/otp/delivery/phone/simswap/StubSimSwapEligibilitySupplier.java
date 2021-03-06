package uk.co.idv.context.adapter.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

import java.time.Clock;
import java.time.Instant;
import java.util.Map;
import java.util.function.Supplier;

import static uk.co.mruoc.duration.calculator.DurationCalculatorUtils.millisBetweenNowAnd;

@Builder
@Data
@Slf4j
public class StubSimSwapEligibilitySupplier implements Supplier<Eligibility> {

    private final Map<String, String> mdc = MDC.getCopyOfContextMap();
    private final StubSimSwapResultFactory resultFactory;
    private final SimSwapConfig config;
    private final OtpPhoneNumber number;
    private final Clock clock;
    private final Delay delay;

    @Override
    public Eligibility get() {
        MDC.setContextMap(mdc);
        Instant start = Instant.now();
        try {
            return perform();
        } finally {
            log.info("sim swap supplier took {}ms for {}", millisBetweenNowAnd(start), number);
            MDC.clear();
        }
    }

    private Eligibility perform() {
        log.info("running stub sim swap eligibility supplier for {}", number);
        if (shouldError()) {
            throw new StubSimSwapErrorException(number.getValue());
        }
        if (shouldBeDelayed()) {
            delay.execute();
        }
        Eligibility eligibility = buildEligibility();
        log.info("sim swap eligibility supplier for {} returned eligibility {}", number, eligibility);
        return eligibility;
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
