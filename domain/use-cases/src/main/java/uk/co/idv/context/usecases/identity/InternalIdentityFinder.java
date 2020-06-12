package uk.co.idv.context.usecases.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.identity.Identity;

@RequiredArgsConstructor
public class InternalIdentityFinder implements IdentityFinder {

    private final IdentityRepository repository;

    @Override
    public Identity find(FindIdentityRequest request) {
        Alias alias = request.getProvidedAlias();
        return repository.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

}
