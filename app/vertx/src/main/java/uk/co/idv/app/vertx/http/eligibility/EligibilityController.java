package uk.co.idv.app.vertx.http.eligibility;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibility;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;

import static uk.co.idv.app.vertx.http.ResponsePopulator.contentTypeJson;

@Slf4j
@RequiredArgsConstructor
public class EligibilityController {

    private final CreateEligibility createEligibility;
    private final EligibilityRoutingContextConverter contextConverter;

    public EligibilityController(CreateEligibility createEligibility) {
        this(createEligibility, new EligibilityRoutingContextConverter());
    }

    public void createEligibility(RoutingContext routingContext) {
        CreateEligibilityRequest request = contextConverter.toRequest(routingContext);
        Eligibility eligibility = createEligibility.create(request);
        created(eligibility, routingContext);
    }

    private void created(Eligibility eligibility, RoutingContext context) {
        var body = JsonObject.mapFrom(eligibility);
        contentTypeJson(context.response())
                .setStatusCode(201)
                .end(body.encode());
    }

}
