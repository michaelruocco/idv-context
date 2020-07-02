package uk.co.idv.app.vertx.http.eligibility;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;

@Slf4j
@RequiredArgsConstructor
public class EligibilityController {

    private final CreateEligibility createEligibility;

    public void createEligibility(RoutingContext routingContext) {
        CreateEligibilityRequest request = extractRequest(routingContext);
        Eligibility eligibility = createEligibility.create(request);
        created(eligibility, routingContext);
    }

    private CreateEligibilityRequest extractRequest(RoutingContext routingContext) {
        var body = routingContext.getBody();
        var json = body.toJsonObject();
        return json.mapTo(CreateEligibilityRequest.class);
    }

    private void created(Eligibility eligibility, RoutingContext context) {
        var body = JsonObject.mapFrom(eligibility);
        json(context.response())
                .setStatusCode(201)
                .end(body.encode());
    }

    private HttpServerResponse json(HttpServerResponse response) {
        return response.putHeader("content-type", "application/json");
    }

}
