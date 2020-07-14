package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;

public interface IdentityRepository {

    Identities load(Aliases aliases);

    void create(Identity identity);

    void update(Identity updated, Identity existing);

    void delete(Aliases aliases);

}
