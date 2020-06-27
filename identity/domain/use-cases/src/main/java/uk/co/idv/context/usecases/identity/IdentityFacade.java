package uk.co.idv.context.usecases.identity;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.find.FindIdentity;
import uk.co.idv.context.usecases.identity.update.UpdateIdentity;

@Builder
public class IdentityFacade {

    private final UpdateIdentity update;
    private final FindIdentity find;

    public static IdentityFacade build(IdentityRepository repository) {
        return IdentityFacade.builder()
                .update(UpdateIdentity.buildInternal(repository))
                .find(new FindIdentity(repository))
                .build();
    }

    public Identity update(Identity identity) {
        return update.update(identity);
    }

    public Identity find(Aliases aliases) {
        return find.find(aliases);
    }

}
