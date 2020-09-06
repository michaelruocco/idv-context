package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;

import java.time.Clock;
import java.util.function.Supplier;

@Builder
public class StubSimSwapResultSupplier implements Supplier<SimSwapResult> {

    private final Clock clock;
    private final OtpPhoneNumber number;

    @Override
    public SimSwapResult get() {
        String value = number.getValue();
        char lastDigit = value.charAt(value.length() - 1);
        switch (lastDigit) {
            case '9':
                return SimSwapResult.failure();
            case '8':
                return SimSwapResult.unknown();
            case '7':
                return SimSwapResult.timeout();
            case '6':
                return SimSwapResult.success(clock.instant());
            default:
                return SimSwapResult.success();
        }
    }

}
