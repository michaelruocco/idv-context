package uk.co.idv.context.usecases.identity.find;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.IdentityNotFoundException;

@RequiredArgsConstructor
public class InternalFindIdentity implements FindIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity find(FindIdentityRequest request) {
        Alias alias = request.getProvidedAlias();
        return repository.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

}
