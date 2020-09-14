package uk.co.idv.context.entities.policy.method;

import java.time.Duration;

public interface MethodConfig {

    int getMaxNumberOfAttempts();

    Duration getDuration();

}
