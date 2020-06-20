package uk.co.idv.context.usecases.identity.find.internal;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.find.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.merge.MultipleIdentitiesFoundException;

import java.util.Collection;

@RequiredArgsConstructor
public class FindIdentity {

    private final IdentityRepository repository;

    public Identity find(Aliases aliases) {
        Collection<Identity> identities = repository.load(aliases);
        switch (identities.size()) {
            case 0:
                throw new IdentityNotFoundException(aliases);
            case 1:
                return identities.iterator().next();
            default:
                throw new MultipleIdentitiesFoundException(aliases, identities);
        }
    }

}
