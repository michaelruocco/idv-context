package uk.co.idv.identity.usecases.identity.create;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.idvid.IdvIdAllocator;

@Builder
@Slf4j
public class CreateIdentity {

    private final IdvIdAllocator idvIdAllocator;
    private final IdentityValidator validator;
    private final IdentityRepository repository;

    public static CreateIdentity build(IdentityRepository repository) {
        return CreateIdentity.builder()
                .idvIdAllocator(new IdvIdAllocator())
                .validator(new IdentityValidator())
                .repository(repository)
                .build();
    }

    public Identity create(Identity identity) {
        validator.validate(identity);
        Identity identityWithId = idvIdAllocator.allocateIfRequired(identity);
        repository.create(identityWithId);
        return identityWithId;
    }

}
