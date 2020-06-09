package uk.co.idv.context.usecases.identity;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.identity.Identity;

import java.util.Optional;

public interface IdentityRepository {

    Optional<Identity> load(Alias alias);

    void save(Identity identity);

}
