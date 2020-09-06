package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.time.Clock;
import java.util.concurrent.CompletableFuture;

@Builder
public class StubSimSwapExecutor implements SimSwapExecutor {

    private final Clock clock;

    @Override
    public Eligibility performSimSwap(OtpPhoneNumber number, SimSwapConfig config) {
        return SimSwapEligibility.builder()
                .now(clock.instant())
                .result(toAsyncResult(number, config))
                .build();
    }

    private AsyncSimSwapResult toAsyncResult(OtpPhoneNumber number, SimSwapConfig config) {
        return AsyncSimSwapResult.builder()
                .timeout(config.getTimeout())
                .futureResult(toFutureResult(number))
                .build();
    }

    private CompletableFuture<SimSwapResult> toFutureResult(OtpPhoneNumber number) {
        return CompletableFuture.completedFuture(toResult(number));
    }

    private SimSwapResult toResult(OtpPhoneNumber number) {
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
                return SimSwapResult.successful(clock.instant());
            default:
                return SimSwapResult.successful();
        }
    }

}
