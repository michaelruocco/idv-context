package uk.co.idv.method.entities.policy;

import uk.co.idv.method.entities.method.MethodConfig;

public interface MethodPolicy extends RequestedDataProvider {

    String getName();

    MethodConfig getConfig();

}
