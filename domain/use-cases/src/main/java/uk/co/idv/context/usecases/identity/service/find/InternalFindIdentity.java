package uk.co.idv.context.usecases.identity.service.find;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.IdentityNotFoundException;
import uk.co.idv.context.usecases.identity.MultipleIdentitiesFoundException;

import java.util.Collection;

@RequiredArgsConstructor
public class InternalFindIdentity implements FindIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity find(FindIdentityRequest request) {
        Aliases aliases = request.getAliases();
        Collection<Identity> identities = repository.load(aliases);
        switch (identities.size()) {
            case 0:
                throw new IdentityNotFoundException(aliases);
            case 1:
                return identities.iterator().next();
            default:
                throw new MultipleIdentitiesFoundException(request.getAliases(), identities);
        }
    }

}
