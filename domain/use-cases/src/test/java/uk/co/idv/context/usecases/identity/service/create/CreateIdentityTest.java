package uk.co.idv.context.usecases.identity.service.create;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CreateIdentityTest {

    private final RandomIdvIdGenerator idGenerator = mock(RandomIdvIdGenerator.class);
    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final CreateIdentity create = CreateIdentity.builder()
            .idGenerator(idGenerator)
            .repository(repository)
            .build();

    @Test
    void shouldSaveIdentityIfIdentityAlreadyHasIdvId() {
        Identity identity = IdentityMother.example();

        Identity created = create.create(identity);

        assertThat(created).isEqualTo(identity);
        verify(idGenerator, never()).generate();
        verify(repository).save(created);
    }

    @Test
    void shouldAllocateIdvIdBeforeSaveIdentityIfIdentityDoesNotHaveIdvId() {
        Identity identity = IdentityMother.withoutIdvId();
        IdvId idvId = IdvIdMother.idvId();
        given(idGenerator.generate()).willReturn(idvId);

        Identity created = create.create(identity);

        assertThat(created).isEqualToIgnoringGivenFields(identity, "aliases");
        assertThat(created.getAliases()).containsAll(identity.getAliases());
        assertThat(created.getIdvId()).isEqualTo(idvId);
        verify(repository).save(created);
    }

}
