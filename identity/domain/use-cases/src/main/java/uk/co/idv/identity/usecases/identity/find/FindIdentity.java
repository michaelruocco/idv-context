package uk.co.idv.identity.usecases.identity.find;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;


@RequiredArgsConstructor
public class FindIdentity {

    private final IdentityRepository repository;

    public Identity find(Alias alias) {
        return repository.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
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
