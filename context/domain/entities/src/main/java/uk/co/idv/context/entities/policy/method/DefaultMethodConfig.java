package uk.co.idv.context.entities.policy.method;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class DefaultMethodConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;

}
