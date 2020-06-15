package uk.co.idv.context.adapter.identity.service.find;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.context.usecases.identity.service.find.data.Delay;
import uk.co.idv.context.usecases.identity.service.find.data.TimeoutProvider;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Builder
@Data
public class StubFindIdentityConfig implements TimeoutProvider {

    private static final Delay NO_DELAY = new Delay(0);

    private final ExecutorService executor;

    @Builder.Default private final Delay phoneNumberDelay = NO_DELAY;
    @Builder.Default private final Delay emailAddressDelay = NO_DELAY;

    @Getter(AccessLevel.NONE)
    @Builder.Default private final Duration timeout = Duration.ofSeconds(2);

    public static StubFindIdentityConfig build(ExecutorService executor) {
        return StubFindIdentityConfig.builder()
                .executor(executor)
                .build();
    }

    @Override
    public Duration getTimeout(String channelId) {
        return timeout;
    }

}
