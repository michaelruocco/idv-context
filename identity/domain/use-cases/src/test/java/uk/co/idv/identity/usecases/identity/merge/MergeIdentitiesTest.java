package uk.co.idv.identity.usecases.identity.merge;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.identity.entities.event.MergeIdentitiesEvent;
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
    private final MergeIdentitiesHandler handler = mock(MergeIdentitiesHandler.class);

    private final MergeIdentities merge = MergeIdentities.builder()
            .idvIdAllocator(allocator)
            .repository(repository)
            .handler(handler)
            .build();

    @Test
    void shouldAllocateIdvIdToIdentity() {
        Identity identity = mock(Identity.class);
        Identity identityWithId = mock(Identity.class);
        given(allocator.allocateIfRequired(identity)).willReturn(identityWithId);
        Identities original = IdentitiesMother.two();
        Identity expectedMergedIdentity = IdentityMother.example();
        given(identityWithId.addData(original)).willReturn(expectedMergedIdentity);

        Identity mergedIdentity = merge.merge(identity, original);

        assertThat(mergedIdentity).isEqualTo(expectedMergedIdentity);
        verify(repository).delete(original.getIdvIds());
        verify(repository).create(mergedIdentity);
    }

    @Test
    void shouldPassEventToHandler() {
        Identity identity = mock(Identity.class);
        Identity identityWithId = mock(Identity.class);
        given(allocator.allocateIfRequired(identity)).willReturn(identityWithId);
        Identities original = IdentitiesMother.two();
        Identity expectedMergedIdentity = IdentityMother.example();
        given(identityWithId.addData(original)).willReturn(expectedMergedIdentity);

        merge.merge(identity, original);

        ArgumentCaptor<MergeIdentitiesEvent> captor = ArgumentCaptor.forClass(MergeIdentitiesEvent.class);
        verify(handler).merged(captor.capture());
        MergeIdentitiesEvent event = captor.getValue();
        assertThat(event.getMerged()).isEqualTo(expectedMergedIdentity);
        assertThat(event.getOriginal()).isEqualTo(original);
    }

}
