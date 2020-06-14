package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdvIdAlreadyPresentExceptionTest {

    private final IdvId idvId = IdvIdMother.withValue("12bf39f8-2620-4ab2-a171-491e1d71b420");
    private final IdvId existingIdvId = IdvIdMother.withValue("fe69a621-a6ea-4d5f-956a-2c35a23f30f7");

    private final IdvIdAlreadyPresentException error = new IdvIdAlreadyPresentException(idvId, existingIdvId);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("idvId already exists");
    }

    @Test
    void shouldReturnIdvIdToAdd() {
        assertThat(error.getIdvIdToAdd()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnExistingIdvId() {
        assertThat(error.getExistingIdvId()).isEqualTo(existingIdvId);
    }

}
