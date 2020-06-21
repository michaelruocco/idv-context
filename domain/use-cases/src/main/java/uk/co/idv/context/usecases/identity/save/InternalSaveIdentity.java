package uk.co.idv.context.usecases.identity.save;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

@RequiredArgsConstructor
public class InternalSaveIdentity implements SaveIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity save(Identity newIdentity, Identity existingIdentity) {
        repository.save(newIdentity);
        return newIdentity;
    }

}
