package uk.co.idv.app.vertx.http;

import io.vertx.core.DeploymentOptions;

public class HttpServerOptions extends DeploymentOptions {

    public HttpServerOptions() {
        setWorker(true);
        setInstances(1);
        setWorkerPoolSize(10);
    }

}
