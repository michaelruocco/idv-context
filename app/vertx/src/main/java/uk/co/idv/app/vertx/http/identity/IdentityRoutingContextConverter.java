package uk.co.idv.app.vertx.http.identity;

import io.vertx.ext.web.RoutingContext;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityFacade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class IdentityRoutingContextConverter {

    private final IdentityFacade facade;

    public Aliases toAliases(RoutingContext context) {
        List<String> types = context.queryParam("aliasType");
        List<String> values = context.queryParam("aliasValue");
        Collection<Alias> aliases = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            aliases.add(toAlias(types.get(i), values.get(i)));
        }
        return new Aliases(aliases);
    }

    public Aliases toIdvId(RoutingContext context) {
        String idvIdValue = context.pathParam("idvId");
        return new Aliases(toAlias("idv-id", idvIdValue));
    }

    public Alias toAlias(String type, String value) {
        return facade.toAlias(type, value);
    }

    public Identity toIdentity(RoutingContext routingContext) {
        var body = routingContext.getBody();
        var json = body.toJsonObject();
        return json.mapTo(Identity.class);
    }

}
