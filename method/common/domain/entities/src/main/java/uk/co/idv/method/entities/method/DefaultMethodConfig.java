package uk.co.idv.method.entities.method;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class DefaultMethodConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;

}
