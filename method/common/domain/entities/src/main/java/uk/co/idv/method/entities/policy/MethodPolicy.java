package uk.co.idv.method.entities.policy;

import uk.co.idv.method.entities.method.MethodConfig;

import java.time.Duration;

public interface MethodPolicy extends RequestedDataProvider {

    String getName();

    MethodConfig getConfig();

    default Duration getDuration() {
        return getConfig().getDuration();
    }

}
