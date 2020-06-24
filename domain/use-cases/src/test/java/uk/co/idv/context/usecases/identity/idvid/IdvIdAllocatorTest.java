package uk.co.idv.context.usecases.identity.idvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class IdvIdAllocatorTest {

    private final RandomIdvIdGenerator idGenerator = mock(RandomIdvIdGenerator.class);

    private final IdvIdAllocator allocator = new IdvIdAllocator(idGenerator);

    @Test
    void shouldReturnIdentityIfAlreadyHasIdvId() {
        Identity identity = IdentityMother.example();

        Identity updated = allocator.allocateIfRequired(identity);

        assertThat(updated).isEqualTo(identity);
        verify(idGenerator, never()).generate();
    }

    @Test
    void shouldAllocateIdvIdIfIdentityDoesNotHaveIdvId() {
        Identity identity = IdentityMother.withoutIdvId();
        IdvId idvId = IdvIdMother.idvId();
        given(idGenerator.generate()).willReturn(idvId);

        Identity updated = allocator.allocateIfRequired(identity);

        assertThat(updated).isEqualToIgnoringGivenFields(identity, "aliases");
        assertThat(updated.getAliases()).containsAll(identity.getAliases());
        assertThat(updated.getIdvId()).isEqualTo(idvId);
    }

}
