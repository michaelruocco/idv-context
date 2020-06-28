package uk.co.idv.context.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InternalSaveIdentityTest {

    private final IdentityRepository repository = mock(IdentityRepository.class);

    private final SaveIdentity save = new InternalSaveIdentity(repository);

    @Test
    void shouldSaveIdentityIgnoringExistingIfBothHaveSameIdvId() {
        Identity update = IdentityMother.example();
        Identity existing = IdentityMother.example();

        Identity saved = save.save(update, existing);

        assertThat(saved).isEqualTo(update);
        verify(repository).save(update);
    }

    @Test
    void shouldSaveIdentityWithExistingIdvIdIfUpdatedIdentityDoesNotHaveOne() {
        Identity update = IdentityMother.withoutIdvId();
        Identity existing = IdentityMother.example1();

        Identity saved = save.save(update, existing);

        Identity expected = update.setIdvId(existing.getIdvId());
        assertThat(saved).isEqualTo(expected);
        verify(repository).save(expected);
    }

    @Test
    void shouldThrowExceptionIfIdvIdsDoNotMatch() {
        Identity update = IdentityMother.example();
        Identity existing = IdentityMother.example1();

        CannotUpdateIdvIdException error = catchThrowableOfType(
                () -> save.save(update, existing),
                CannotUpdateIdvIdException.class
        );

        assertThat(error.getUpdated()).isEqualTo(update.getIdvId());
        assertThat(error.getExisting()).isEqualTo(existing.getIdvId());
    }

}
