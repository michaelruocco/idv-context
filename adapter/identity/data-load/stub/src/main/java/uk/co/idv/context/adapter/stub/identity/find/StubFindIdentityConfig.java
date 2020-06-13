package uk.co.idv.context.adapter.stub.identity.find;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.adapter.stub.identity.find.data.Delay;

import java.util.concurrent.ExecutorService;

@Data
@Builder
public class StubFindIdentityConfig {

    private static final Delay NO_DELAY = new Delay(0);

    private final ExecutorService executor;

    @Builder.Default private final Delay aliasDelay = NO_DELAY;
    @Builder.Default private final Delay phoneNumberDelay = NO_DELAY;
    @Builder.Default private final Delay emailAddressDelay = NO_DELAY;

    public static StubFindIdentityConfig build(ExecutorService executor) {
        return StubFindIdentityConfig.builder()
                .executor(executor)
                .build();
    }

}
