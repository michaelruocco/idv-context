package uk.co.idv.context.usecases.identity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.identity.Identity;

@RequiredArgsConstructor
public class DefaultIdentityLoader implements IdentityLoader {

    private final IdentityRepository repository;

    @Override
    public Identity load(LoadIdentityRequest request) {
        Alias alias = request.getAlias();
        return repository.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

}
