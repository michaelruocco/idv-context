package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.async.DelayedSupplier;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.eligibility.Ineligible;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AsyncSimSwapEligibilityTest {

    @Test
    void shouldReturnFuture() {
        CompletableFuture<Eligibility> future = CompletableFuture.completedFuture(mock(Eligibility.class));

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .build();

        assertThat(eligibility.getFuture()).isEqualTo(future);
    }

    @Test
    void shouldReturnSimSwapConfig() {
        SimSwapConfig config = mock(SimSwapConfig.class);

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .config(config)
                .build();

        assertThat(eligibility.getConfig()).isEqualTo(config);
    }

    @Test
    void shouldEligibleIfFutureEligibilityIsEligible() {
        CompletableFuture<Eligibility> future = CompletableFuture.completedFuture(new Eligible());

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .build();

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnEmptyReasonIfFutureEligibilityIsEligible() {
        CompletableFuture<Eligibility> future = CompletableFuture.completedFuture(new Eligible());

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .build();

        assertThat(eligibility.getReason()).isEmpty();
    }

    @Test
    void shouldIneligibleIfFutureEligibilityIsIneligible() {
        CompletableFuture<Eligibility> future = CompletableFuture.completedFuture(new Ineligible("test reason"));

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .build();

        assertThat(eligibility.isEligible()).isFalse();
    }

    @Test
    void shouldReasonFromIfFutureEligibilityIsIneligible() {
        String expectedReason = "test reason";
        CompletableFuture<Eligibility> future = CompletableFuture.completedFuture(new Ineligible(expectedReason));

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .build();

        assertThat(eligibility.getReason()).contains(expectedReason);
    }

    @Test
    void shouldEligibilityWithUnknownStatusEligibleIfFutureFailsAndUnknownStatusIsAcceptable() {
        CompletableFuture<Eligibility> future = CompletableFuture.failedFuture(new Exception("test error"));
        SimSwapConfig config = mock(SimSwapConfig.class);
        given(config.isAcceptable("unknown")).willReturn(true);

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .config(config)
                .build();

        assertThat(eligibility.isEligible()).isTrue();
        assertThat(eligibility.getReason()).isEmpty();
    }

    @Test
    void shouldEligibilityWithUnknownStatusIneligibleIfFutureFailsAndUnknownStatusIsNotAcceptable() {
        CompletableFuture<Eligibility> future = CompletableFuture.failedFuture(new Exception("test error"));
        SimSwapConfig config = mock(SimSwapConfig.class);
        given(config.isAcceptable("unknown")).willReturn(false);

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .config(config)
                .build();

        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains("sim swap status unknown not acceptable");
    }

    @Test
    void shouldEligibilityWithTimeoutStatusIneligibleIfFutureIsNotComplete() {
        CompletableFuture<Eligibility> future = CompletableFuture.supplyAsync(new DelayedSupplier<>(Duration.ofDays(1), mock(Eligibility.class)));
        SimSwapConfig config = mock(SimSwapConfig.class);
        given(config.isAcceptable("timeout")).willReturn(false);

        AsyncSimSwapEligibility eligibility = AsyncSimSwapEligibility.builder()
                .future(future)
                .config(config)
                .build();

        assertThat(eligibility.isEligible()).isFalse();
        assertThat(eligibility.getReason()).contains("sim swap status timeout not acceptable");
    }

}
