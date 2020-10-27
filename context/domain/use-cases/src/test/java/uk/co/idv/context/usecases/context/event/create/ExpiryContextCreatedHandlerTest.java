package uk.co.idv.context.usecases.context.event.create;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.event.expiry.ExpiryHandler;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ExpiryContextCreatedHandlerTest {

    private final Clock clock = mock(Clock.class);
    private final ScheduledExecutorService executor = mock(ScheduledExecutorService.class);

    private final ExpiryContextCreatedHandler handler = ExpiryContextCreatedHandler.builder()
            .clock(clock)
            .executor(executor)
            .build();

    @Test
    void shouldScheduleExpiryHandlerWhenContextExpires() {
        Duration expectedDelay = Duration.ofMillis(5);
        Instant now = Instant.now();
        givenCurrentTime(now);
        Context context = givenContextWithExpiry(now.plus(expectedDelay));

        handler.created(context);

        ArgumentCaptor<ExpiryHandler> captor = ArgumentCaptor.forClass(ExpiryHandler.class);
        verify(executor).schedule(captor.capture(), eq(expectedDelay.toSeconds()), eq(TimeUnit.SECONDS));
        ExpiryHandler handler = captor.getValue();
        assertThat(handler.getContext()).isEqualTo(context);
    }

    private void givenCurrentTime(Instant now) {
        given(clock.instant()).willReturn(now);
    }

    private Context givenContextWithExpiry(Instant expiry) {
        Context context = mock(Context.class);
        given(context.getExpiry()).willReturn(expiry);
        return context;
    }

}
