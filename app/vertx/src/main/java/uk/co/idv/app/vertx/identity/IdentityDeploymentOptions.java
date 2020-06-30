package uk.co.idv.app.vertx.identity;

import io.vertx.core.DeploymentOptions;

public class IdentityDeploymentOptions extends DeploymentOptions {

    public IdentityDeploymentOptions() {
        setWorker(true);
        setInstances(1);
        setWorkerPoolSize(10);
    }

}
