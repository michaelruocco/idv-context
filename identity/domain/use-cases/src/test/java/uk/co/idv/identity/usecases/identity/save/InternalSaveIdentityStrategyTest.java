package uk.co.idv.identity.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.usecases.identity.save.internal.InternalSaveIdentityStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class InternalSaveIdentityStrategyTest {

    private final SaveIdentityStrategy strategy = new InternalSaveIdentityStrategy();

    @Test
    void shouldSaveIdentityIgnoringExistingIfBothHaveSameIdvId() {
        Identity update = IdentityMother.example();
        Identity existing = IdentityMother.example();

        Identity saved = strategy.prepare(update, existing);

        assertThat(saved).isEqualTo(update);
    }

    @Test
    void shouldSaveIdentityWithExistingIdvIdIfUpdatedIdentityDoesNotHaveOne() {
        Identity update = IdentityMother.withoutIdvId();
        Identity existing = IdentityMother.example1();

        Identity saved = strategy.prepare(update, existing);

        Identity expected = update.setIdvId(existing.getIdvId());
        assertThat(saved).isEqualTo(expected);
    }

    @Test
    void shouldThrowExceptionIfIdvIdsDoNotMatch() {
        Identity update = IdentityMother.example();
        Identity existing = IdentityMother.example1();

        CannotUpdateIdvIdException error = catchThrowableOfType(
                () -> strategy.prepare(update, existing),
                CannotUpdateIdvIdException.class
        );

        assertThat(error.getUpdated()).isEqualTo(update.getIdvId());
        assertThat(error.getExisting()).isEqualTo(existing.getIdvId());
    }

}
