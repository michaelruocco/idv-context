package uk.co.idv.app.vertx.identity;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import uk.co.idv.context.adapter.json.error.ApiError;
import uk.co.idv.context.adapter.json.error.handler.ErrorHandler;
import uk.co.idv.context.adapter.json.error.handler.IdentityErrorHandler;

public class IdentityExceptionHandler implements Handler<RoutingContext> {

    private final ErrorHandler errorHandler = new IdentityErrorHandler();

    @Override
    public void handle(final RoutingContext context) {
        ApiError error = errorHandler.apply(context.failure());
        json(context.response())
                .setStatusCode(error.getStatus())
                .end(toBody(error));
    }

    private String toBody(final ApiError error) {
        return JsonObject.mapFrom(error).encode();
    }

    private HttpServerResponse json(HttpServerResponse response) {
        return response.putHeader("content-type", "application/json");
    }

}
