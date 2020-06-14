package uk.co.idv.context.usecases.identity.update;

import lombok.Builder;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

@Builder
public class InternalUpdateIdentity implements UpdateIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity update(Identity identity) {
        repository.save(identity);
        return identity;
    }

}
