package uk.co.idv.context.adapter.stub.identity.find;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.usecases.identity.find.data.Delay;
import uk.co.idv.context.usecases.identity.find.data.TimeoutProvider;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Data
@Builder
public class StubFindIdentityConfig implements TimeoutProvider {

    private static final Delay NO_DELAY = new Delay(0);

    private final ExecutorService executor;

    @Builder.Default private final Delay phoneNumberDelay = NO_DELAY;
    @Builder.Default private final Delay emailAddressDelay = NO_DELAY;
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
