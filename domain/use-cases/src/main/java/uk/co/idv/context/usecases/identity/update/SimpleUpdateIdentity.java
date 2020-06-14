package uk.co.idv.context.usecases.identity.update;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

@RequiredArgsConstructor
public class SimpleUpdateIdentity implements UpdateIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity update(Identity identity) {
        repository.save(identity);
        return identity;
    }

}
