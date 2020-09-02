package uk.co.idv.identity.usecases.identity.save;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import static org.assertj.core.api.Assertions.assertThat;

class CannotUpdateIdvIdExceptionTest {

    private final IdvId updated = IdvIdMother.idvId();
    private final IdvId existing = IdvIdMother.idvId1();

    private final CannotUpdateIdvIdException error = new CannotUpdateIdvIdException(updated, existing);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("cannot update idv id");
    }

    @Test
    void shouldReturnAliases() {
        assertThat(error.getUpdated()).isEqualTo(updated);
    }

    @Test
    void shouldReturnExistingIdentities() {
        assertThat(error.getExisting()).isEqualTo(existing);
    }

}
