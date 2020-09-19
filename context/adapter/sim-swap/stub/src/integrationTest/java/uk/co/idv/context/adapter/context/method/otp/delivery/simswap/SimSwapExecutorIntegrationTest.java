package uk.co.idv.context.adapter.context.method.otp.delivery.simswap;

import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap.StubSimSwapExecutorConfig;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatusesMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap.SimSwapExecutor;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class SimSwapExecutorIntegrationTest {

    private static final long MIN_DAYS_SINCE_SIM_SWAP = 6;
    private static final Instant NOW = Instant.parse("2020-09-13T20:01:01.001Z");
    private final Delay delay = new Delay(Duration.ofMillis(250));

    private final SimSwapConfig simSwapConfig = SimSwapConfig.builder()
            .minDaysSinceSwap(MIN_DAYS_SINCE_SIM_SWAP)
            .acceptableStatuses(AcceptableSimSwapStatusesMother.onlySuccess())
            .build();

    private final StubSimSwapExecutorConfig config = StubSimSwapExecutorConfig.builder()
            .clock(Clock.fixed(NOW, ZoneId.systemDefault()))
            .delay(delay)
            .executorService(Executors.newFixedThreadPool(1))
            .build();

    private final SimSwapExecutor executor = config.simSwapExecutor();

    @Test
    void shouldReturnIneligibleFailureResultForNumberEndingIn9() {
        OtpPhoneNumber number = buildNumberEndingIn(9);

        AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

        waitForCompletion(eligibility);
        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains("sim swap status failure is not allowed");
    }

    @Test
    void shouldReturnIneligibleUnknownResultForNumberEndingIn8() {
        OtpPhoneNumber number = buildNumberEndingIn(8);

        AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

        waitForCompletion(eligibility);
        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains("sim swap status unknown is not allowed");
    }

    @Test
    void shouldReturnIneligibleTimeoutResultForNumberEndingIn7() {
        OtpPhoneNumber number = buildNumberEndingIn(7);

        AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

        waitForCompletion(eligibility);
        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains("sim swap status timeout is not allowed");
    }

    @Test
    void shouldReturnIneligibleSuccessResultWithLastSwappedDateForNumberEndingIn6() {
        OtpPhoneNumber number = buildNumberEndingIn(6);

        AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

        waitForCompletion(eligibility);
        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains(buildExpectedLastSwappedDateCutoffReason());
    }

    @Test
    void shouldReturnEligibleSuccessResultForNumberEndingIn5AfterDelay() {
        Instant start = Instant.now();
        try {
            OtpPhoneNumber number = buildNumberEndingIn(5);

            AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

            waitForCompletion(eligibility);
            assertThat(eligibility.isEligible()).isTrue();
        } finally {
            assertThat(Duration.between(start, Instant.now())).isGreaterThan(delay.getDuration());
        }
    }

    @Test
    void shouldReturnIneligibleAfterErrorForNumberEndingIn4() {
        OtpPhoneNumber number = buildNumberEndingIn(4);

        AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

        waitForCompletion(eligibility);
        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains("sim swap status unknown not acceptable");
    }

    @Test
    void shouldReturnEligibleSuccessResultForNumberEndingIn3() {
        OtpPhoneNumber number = buildNumberEndingIn(3);

        AsyncSimSwapEligibility eligibility = executor.executeSimSwap(number, simSwapConfig);

        waitForCompletion(eligibility);
        assertThat(eligibility.isEligible()).isTrue();
    }

    private static OtpPhoneNumber buildNumberEndingIn(int lastDigit) {
        String value = String.format("+44780912312%d", lastDigit);
        return OtpPhoneNumberMother.withValue(value);
    }

    private static void waitForCompletion(AsyncSimSwapEligibility eligibility) {
        Awaitility.await().atMost(Duration.ofMillis(500))
                .pollDelay(Duration.ofMillis(50))
                .until(() -> eligibility.getFuture().isDone());
    }

    private static String buildExpectedLastSwappedDateCutoffReason() {
        Instant swappedAt = NOW.minus(Duration.ofDays(5));
        Instant cutoff = NOW.minus(Duration.ofDays(MIN_DAYS_SINCE_SIM_SWAP));
        return String.format("sim swapped at %s (cutoff %s)", swappedAt, cutoff);
    }

}
