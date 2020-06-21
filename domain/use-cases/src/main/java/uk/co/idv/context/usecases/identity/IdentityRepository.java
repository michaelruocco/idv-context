package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Collection;
import java.util.Optional;

public interface IdentityRepository {

    Optional<Identity> load(Alias alias);

    Collection<Identity> load(Aliases aliases);

    void save(Identity identity);

    void delete(Alias alias);

}
