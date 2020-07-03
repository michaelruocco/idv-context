package uk.co.idv.app.vertx.http.identity;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityFacade;

import static uk.co.idv.app.vertx.http.ResponsePopulator.contentTypeJson;

@Slf4j
@RequiredArgsConstructor
public class IdentityController {

    private final IdentityFacade facade;
    private final IdentityRoutingContextConverter contextConverter;

    public IdentityController(IdentityFacade facade) {
        this(facade, new IdentityRoutingContextConverter(facade));
    }

    public void getIdentityByAlias(RoutingContext context) {
        Aliases aliases = contextConverter.toAliases(context);
        Identity identity = facade.find(aliases);
        ok(identity, context);
    }

    public void getIdentityByIdvId(RoutingContext context) {
        Aliases aliases = contextConverter.toIdvId(context);
        Identity identity = facade.find(aliases);
        ok(identity, context);
    }

    public void updateIdentity(RoutingContext routingContext) {
        Identity input = contextConverter.toIdentity(routingContext);
        Identity updated = facade.update(input);
        ok(updated, routingContext);
    }

    private void ok(Identity identity, RoutingContext context) {
        var body = JsonObject.mapFrom(identity);
        contentTypeJson(context.response())
                .putHeader("Location", toLocation(context.request(), identity))
                .setStatusCode(200)
                .end(body.encode());
    }

    private String toLocation(HttpServerRequest request, Identity identity) {
        return String.format("%s/%s", request.absoluteURI(), identity.getIdvIdValue());
    }

}
