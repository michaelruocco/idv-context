package uk.co.idv.identity.usecases.identity.merge;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.IdentitiesMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.IdentityRepository;
import uk.co.idv.identity.usecases.identity.idvid.IdvIdAllocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MergeIdentitiesTest {

    private final IdvIdAllocator allocator = mock(IdvIdAllocator.class);
    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final MergeIdentities merge = MergeIdentities.builder()
            .idvIdAllocator(allocator)
            .repository(repository)
            .build();

    @Test
    void shouldAllocateIdvIdToIdentity() {
        Identity identity = mock(Identity.class);
        Identity identityWithId = mock(Identity.class);
        given(allocator.allocateIfRequired(identity)).willReturn(identityWithId);
        Identities existing = IdentitiesMother.two();
        Identity expectedMergedIdentity = IdentityMother.example();
        given(identityWithId.addData(existing)).willReturn(expectedMergedIdentity);

        Identity mergedIdentity = merge.merge(identity, existing);

        assertThat(mergedIdentity).isEqualTo(expectedMergedIdentity);
        verify(repository).delete(existing.getIdvIds());
        verify(repository).create(mergedIdentity);
    }

}
