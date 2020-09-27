package uk.co.idv.identity.adapter.eligibility.external;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.usecases.eligibility.external.data.TimeoutProvider;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Builder
@Data
@Slf4j
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
