package uk.co.idv.context.entities.policy.method.fake;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.MethodConfig;

import java.time.Duration;

@Builder
@Data
public class FakeMethodConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;

}
