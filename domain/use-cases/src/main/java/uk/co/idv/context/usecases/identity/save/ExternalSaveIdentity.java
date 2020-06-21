package uk.co.idv.context.usecases.identity.save;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

@RequiredArgsConstructor
public class ExternalSaveIdentity implements SaveIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity save(Identity newIdentity, Identity existingIdentity) {
        Identity identityToSave = newIdentity.addData(existingIdentity);
        repository.save(identityToSave);
        return identityToSave;
    }

}
