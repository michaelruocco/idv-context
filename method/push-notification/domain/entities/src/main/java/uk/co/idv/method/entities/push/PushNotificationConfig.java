package uk.co.idv.method.entities.push;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.method.MethodConfig;

import java.time.Duration;

@Builder
@Data
public class PushNotificationConfig implements MethodConfig {

    private final int maxNumberOfAttempts;
    private final Duration duration;

}
