package uk.co.idv.context.usecases.identity.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;
import uk.co.idv.context.usecases.identity.idvid.IdvIdAllocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CreateIdentityTest {

    private final IdvIdAllocator allocator = mock(IdvIdAllocator.class);
    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final CreateIdentity create = CreateIdentity.builder()
            .idvIdAllocator(allocator)
            .repository(repository)
            .build();

    @Test
    void shouldAllocateIdvIdIfRequiredBeforeSaveIdentityIfIdentityDoesNotHaveIdvId() {
        Identity identity = IdentityMother.withoutIdvId();
        Identity identityWithId = IdentityMother.example();
        given(allocator.allocateIfRequired(identity)).willReturn(identityWithId);

        Identity created = create.create(identity);

        assertThat(created).isEqualTo(identityWithId);
        verify(repository).save(identityWithId);
    }

}
