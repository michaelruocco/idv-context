package uk.co.idv.method.entities.fake.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.method.MethodConfig;

import java.time.Duration;

@Builder
@Data
public class FakeMethodConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;
    private final String fakeValue;

}
