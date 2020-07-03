package uk.co.idv.app.vertx.http.eligibility;

import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;

@RequiredArgsConstructor
public class EligibilityRoutingContextConverter {

    public CreateEligibilityRequest toRequest(RoutingContext routingContext) {
        var body = routingContext.getBody();
        var json = body.toJsonObject();
        return json.mapTo(CreateEligibilityRequest.class);
    }

}
