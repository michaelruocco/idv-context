package uk.co.idv.app.vertx.identity;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityFacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class IdentityController {

    private final IdentityFacade facade;

    public void getIdentity(RoutingContext routingContext) {
        Aliases aliases = extractAliases(routingContext);
        Identity identity = facade.find(aliases);
        ok(identity, routingContext);
    }

    public void updateIdentity(RoutingContext routingContext) {
        Identity input = extractIdentity(routingContext);
        Identity updated = facade.update(input);
        ok(updated, routingContext);
    }

    private Aliases extractAliases(RoutingContext routingContext) {
        List<String> types = routingContext.queryParam("aliasType");
        List<String> values = routingContext.queryParam("aliasValue");
        Collection<Alias> aliases = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            aliases.add(facade.toAlias(types.get(i), values.get(i)));
        }
        return new Aliases(aliases);
    }

    private Identity extractIdentity(RoutingContext routingContext) {
        var body = routingContext.getBody();
        var json = body.toJsonObject();
        return json.mapTo(Identity.class);
    }

    private void ok(Identity identity, RoutingContext context) {
        var body = JsonObject.mapFrom(identity);
        json(context.response())
                .putHeader("Location", toLocation(context.request(), identity))
                .setStatusCode(200)
                .end(body.encode());
    }

    private String toLocation(final HttpServerRequest request, final Identity identity) {
        return String.format("%s/%s", request.absoluteURI(), identity.getIdvIdValue());
    }

    private HttpServerResponse json(HttpServerResponse response) {
        return response.putHeader("content-type", "application/json");
    }

}
