package uk.co.idv.context.usecases.identity.save;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

@RequiredArgsConstructor
public class ExternalSaveIdentity implements SaveIdentity {

    private final IdentityRepository repository;

    @Override
    public Identity save(Identity update, Identity existing) {
        Identity save = update.addData(existing);
        repository.save(save);
        return save;
    }

}
