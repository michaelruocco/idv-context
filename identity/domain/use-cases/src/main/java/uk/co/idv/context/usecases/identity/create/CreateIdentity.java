package uk.co.idv.context.usecases.identity.create;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.idvid.IdvIdAllocator;

@Builder
@Slf4j
public class CreateIdentity {

    private final IdvIdAllocator idvIdAllocator;
    private final IdentityRepository repository;

    public static CreateIdentity build(IdentityRepository repository) {
        return CreateIdentity.builder()
                .idvIdAllocator(new IdvIdAllocator())
                .repository(repository)
                .build();
    }

    public Identity create(Identity identity) {
        Identity identityWithId = idvIdAllocator.allocateIfRequired(identity);
        repository.save(identityWithId);
        return identityWithId;
    }

}
