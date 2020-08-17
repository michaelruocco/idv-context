package uk.co.idv.context.usecases.identity.find;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;


@RequiredArgsConstructor
public class FindIdentity {

    private final IdentityRepository repository;

    public Identity find(Alias... aliases) {
        return find(new Aliases(aliases));
    }

    public Identity find(Aliases aliases) {
        Identities identities = repository.load(aliases);
        switch (identities.size()) {
            case 0:
                throw new IdentityNotFoundException(aliases);
            case 1:
                return identities.getFirst();
            default:
                throw new MultipleIdentitiesFoundException(aliases, identities);
        }
    }

}
