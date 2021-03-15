package uk.co.idv.policy.usecases.policy.cache;

import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.duration.calculator.DurationCalculatorUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CacheControllerTest {

    @Test
    void shouldSetUpdatingCacheToTrueWhenUpdateStarted() {
        CacheController controller = CacheController.builder()
                .updatingCache(new AtomicBoolean(false))
                .build();

        controller.startUpdate();

        assertThat(controller.isUpdating()).isTrue();
    }

    @Test
    void shouldSetUpdatingCacheToTrueFalseWhenUpdateCompleted() {
        CacheController controller = CacheController.builder()
                .updatingCache(new AtomicBoolean(true))
                .build();

        controller.completeUpdate();

        assertThat(controller.isUpdating()).isFalse();
    }

    @Test
    void shouldWaitUntilUpdateIsCompleteAndThenThrowExceptionIfDoesNotComplete() {
        Duration maxWait = Duration.ofMillis(500);
        CacheController controller = CacheController.builder()
                .maxWait(maxWait)
                .pollInterval(Duration.ofMillis(250))
                .updatingCache(new AtomicBoolean(true))
                .build();

        Instant start = Instant.now();
        Throwable error = catchThrowable(controller::waitUntilUpdateComplete);
        Duration duration = DurationCalculatorUtils.durationBetweenNowAnd(start);

        assertThat(error).isInstanceOf(ConditionTimeoutException.class);
        assertThat(duration).isGreaterThan(maxWait);
    }

    @Test
    void shouldReturnImmediatelyWhenCacheIsNotUpdating() {
        CacheController controller = CacheController.builder()
                .updatingCache(new AtomicBoolean(false))
                .build();

        Instant start = Instant.now();
        controller.waitUntilUpdateComplete();
        Duration duration = DurationCalculatorUtils.durationBetweenNowAnd(start);

        Duration allowedVariance = Duration.ofMillis(50);
        assertThat(duration).isCloseTo(Duration.ZERO, allowedVariance);
    }

}
