package uk.co.idv.context.adapter.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Builder
@Slf4j
public class StubSimSwapResultFactory {

    private static final Map<Integer, String> STATUSES = buildStatuses();

    private final Clock clock;

    public SimSwapResult build(OtpPhoneNumber number, SimSwapConfig config) {
        log.debug("building sim swap result for {}", number);
        int lastDigit = number.getLastDigit();
        return SimSwapResult.builder()
                .config(config)
                .status(toStatus(lastDigit))
                .lastSwapped(toLastSwapped(lastDigit))
                .build();
    }

    private String toStatus(int lastDigit) {
        return STATUSES.getOrDefault(lastDigit, "success");
    }

    private Instant toLastSwapped(int lastDigit) {
        if (lastDigit == 6) {
            return calculateLastSwapped();
        }
        return null;
    }

    private Instant calculateLastSwapped() {
        return clock.instant().minus(Duration.ofDays(5));
    }

    private static Map<Integer, String> buildStatuses() {
        return Map.of(
                9, "failure",
                8, "unknown",
                7, "timeout"
        );
    }

}
