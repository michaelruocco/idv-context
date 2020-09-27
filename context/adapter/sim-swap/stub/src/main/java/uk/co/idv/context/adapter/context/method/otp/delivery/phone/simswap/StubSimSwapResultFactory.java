package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.SimSwapResult.SimSwapResultBuilder;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Builder
@Slf4j
public class StubSimSwapResultFactory {

    private final Clock clock;

    public SimSwapResult build(OtpPhoneNumber number, SimSwapConfig config) {
        log.debug("building sim swap result for {}", number);
        SimSwapResultBuilder builder = SimSwapResult.builder().config(config);
        switch (number.getLastDigit()) {
            case 9:
                return toFailure(builder);
            case 8:
                return toUnknown(builder);
            case 7:
                return toTimeout(builder);
            case 6:
                return toSuccessWithLastSwapped(builder);
            default:
                return toSuccess(builder);
        }
    }

    private SimSwapResult toFailure(SimSwapResultBuilder builder) {
        return builder.status("failure").build();
    }

    private SimSwapResult toUnknown(SimSwapResultBuilder builder) {
        return builder.status("unknown").build();
    }

    private SimSwapResult toTimeout(SimSwapResultBuilder builder) {
        return builder.status("timeout").build();
    }

    private SimSwapResult toSuccessWithLastSwapped(SimSwapResultBuilder builder) {
        return toSuccess(builder.lastSwapped(calculateLastSwapped()));
    }

    private Instant calculateLastSwapped() {
        return clock.instant().minus(Duration.ofDays(5));
    }

    private SimSwapResult toSuccess(SimSwapResultBuilder builder) {
        return builder.status("success").build();
    }

}
