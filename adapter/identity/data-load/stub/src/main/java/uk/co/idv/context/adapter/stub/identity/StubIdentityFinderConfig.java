package uk.co.idv.context.adapter.stub.identity;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.adapter.stub.identity.data.Delay;

import java.util.concurrent.ExecutorService;

@Data
@Builder
public class StubIdentityFinderConfig {

    private static final Delay NO_DELAY = new Delay(0);

    private final ExecutorService executor;

    @Builder.Default private final Delay aliasDelay = NO_DELAY;
    @Builder.Default private final Delay phoneNumberDelay = NO_DELAY;
    @Builder.Default private final Delay emailAddressDelay = NO_DELAY;

    public static StubIdentityFinderConfig build(ExecutorService executor) {
        return StubIdentityFinderConfig.builder()
                .executor(executor)
                .build();
    }

}
