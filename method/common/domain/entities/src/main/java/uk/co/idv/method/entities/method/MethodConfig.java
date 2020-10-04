package uk.co.idv.method.entities.method;

import java.time.Duration;

public interface MethodConfig {

    int getMaxNumberOfAttempts();

    Duration getDuration();

}
