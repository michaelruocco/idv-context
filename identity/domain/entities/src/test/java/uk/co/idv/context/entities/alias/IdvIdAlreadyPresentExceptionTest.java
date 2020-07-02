package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdvIdAlreadyPresentExceptionTest {

    private final IdvId existingIdvId = IdvIdMother.withValue("fe69a621-a6ea-4d5f-956a-2c35a23f30f7");
    private final IdvId idvIdToAdd = IdvIdMother.withValue("12bf39f8-2620-4ab2-a171-491e1d71b420");

    private final IdvIdAlreadyPresentException error = new IdvIdAlreadyPresentException(existingIdvId, idvIdToAdd);

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("idvId already exists");
    }

    @Test
    void shouldReturnExistingIdvId() {
        assertThat(error.getExisting()).isEqualTo(existingIdvId);
    }

    @Test
    void shouldReturnIdvIdToAdd() {
        assertThat(error.getUpdated()).isEqualTo(idvIdToAdd);
    }

}
