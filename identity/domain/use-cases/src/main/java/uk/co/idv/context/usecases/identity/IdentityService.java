package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.alias.AliasFactory;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliasFactory;
import uk.co.idv.context.entities.alias.DefaultAliases;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

import java.util.UUID;

@Builder
public class IdentityService {

    private final UpdateIdentity update;
    private final FindIdentity find;
    private final AliasFactory aliasFactory;

    public static IdentityService build(IdentityRepository repository) {
        return IdentityService.builder()
                .update(UpdateIdentity.buildInternal(repository))
                .find(new FindIdentity(repository))
                .aliasFactory(new DefaultAliasFactory())
                .build();
    }

    public Identity update(Identity identity) {
        return update.update(identity);
    }

    public Identity find(Aliases aliases) {
        return find.find(aliases);
    }

    public Identity find(String aliasType, String aliasValue) {
        return find.find(toAliases(aliasType, aliasValue));
    }

    public Identity find(UUID idvId) {
        return find.find(toAliases(IdvId.TYPE, idvId.toString()));
    }

    private Aliases toAliases(String type, String value) {
        return new DefaultAliases(aliasFactory.build(type, value));
    }

}
