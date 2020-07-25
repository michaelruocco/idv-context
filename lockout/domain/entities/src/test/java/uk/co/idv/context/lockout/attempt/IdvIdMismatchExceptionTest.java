package uk.co.idv.context.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdvIdMismatchExceptionTest {

    private final IdvId idvId1 = IdvIdMother.idvId();
    private final IdvId idvId2 = IdvIdMother.idvId1();

    private final IdvIdMismatchException error = new IdvIdMismatchException(idvId1, idvId2);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("idv ids do not match");
    }

    @Test
    void shouldReturnIdvIds() {
        assertThat(error.getIdvIds()).containsExactly(idvId1, idvId2);
    }

}
