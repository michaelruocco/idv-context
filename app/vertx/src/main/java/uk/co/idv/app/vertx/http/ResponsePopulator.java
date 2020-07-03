package uk.co.idv.app.vertx.http;

import io.vertx.core.http.HttpServerResponse;

public interface ResponsePopulator {

    static HttpServerResponse contentTypeJson(HttpServerResponse response) {
        return response.putHeader("content-type", "application/json");
    }

}
