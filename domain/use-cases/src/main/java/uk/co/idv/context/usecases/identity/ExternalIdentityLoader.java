package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.data.AliasLoader;

@Builder
public class ExternalIdentityLoader implements IdentityLoader {

    private final AliasLoader aliasLoader;

    @Override
    public Identity load(LoadIdentityRequest request) {
        Aliases loadedAliases = aliasLoader.load(request);
        Aliases allAliases = loadedAliases.add(request.getProvidedAlias());
        LoadIdentityRequest updatedRequest = request.setAliases(allAliases);
        return Identity.builder()
                .aliases(updatedRequest.getAliases())
                .build();
    }

}
