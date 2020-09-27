package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;


import java.time.Duration;

public interface SimSwapConfigMother {

    static SimSwapConfig build() {
        return builder().build();
    }

    static SimSwapConfig async() {
        return builder().async(true).build();
    }

    static SimSwapConfig withTimeout(Duration timeout) {
        return builder().timeout(timeout).build();
    }

    static SimSwapConfig withoutMinDays() {
        return builder().minDaysSinceSwap(null).build();
    }

    static SimSwapConfig.SimSwapConfigBuilder builder() {
        return SimSwapConfig.builder()
                .acceptableStatuses(AcceptableSimSwapStatusesMother.onlySuccess())
                .timeout(Duration.ofSeconds(2))
                .minDaysSinceSwap(5L)
                .async(false);
    }

}
