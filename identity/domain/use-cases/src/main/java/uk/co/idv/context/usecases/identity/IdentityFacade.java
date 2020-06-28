package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasFactory;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliasFactory;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class IdentityFacade {

    private final UpdateIdentity update;
    private final FindIdentity find;
    private final AliasFactory aliasFactory;

    public static IdentityFacade build(IdentityRepository repository) {
        return IdentityFacade.builder()
                .update(UpdateIdentity.buildInternal(repository))
                .find(new FindIdentity(repository))
                .aliasFactory(new DefaultAliasFactory())
                .build();
    }

    public Alias toAlias(String type, String value) {
        return aliasFactory.build(type, value);
    }

    public Identity update(Identity identity) {
        return update.update(identity);
    }

    public Identity find(Aliases aliases) {
        return find.find(aliases);
    }

}
