package uk.co.idv.context.adapter.eligibility.external;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.identity.usecases.eligibility.external.data.TimeoutProvider;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Builder
@Data
public class ExternalFindIdentityStubConfig implements TimeoutProvider {

    private final ExecutorService executor;

    @Builder.Default private final Duration phoneNumberDelay = Duration.ofSeconds(0);
    @Builder.Default private final Duration emailAddressDelay = Duration.ofSeconds(0);

    @Getter(AccessLevel.NONE)
    @Builder.Default private final Duration timeout = Duration.ofSeconds(2);

    public static ExternalFindIdentityStubConfig build(ExecutorService executor) {
        return ExternalFindIdentityStubConfig.builder()
                .executor(executor)
                .build();
    }

    @Override
    public Duration getTimeout(String channelId) {
        return timeout;
    }

}
