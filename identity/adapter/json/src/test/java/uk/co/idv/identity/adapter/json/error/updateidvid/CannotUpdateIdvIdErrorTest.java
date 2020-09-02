package uk.co.idv.identity.adapter.json.error.updateidvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.json.error.ApiError;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class CannotUpdateIdvIdErrorTest {

    private final IdvId existing = IdvIdMother.idvId1();
    private final IdvId updated = IdvIdMother.idvId();

    private final ApiError error = CannotUpdateIdvIdError.builder()
            .existing(existing)
            .updated(updated)
            .build();

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Cannot update idv id");
    }

    @Test
    void shouldReturnMessage() {
        String expectedMessage = String.format(
                "attempted to update existing value %s to %s",
                existing.getValue(),
                updated.getValue()
        );

        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).contains(
                entry("existing", existing.getValue()),
                entry("new", updated.getValue())
        );
    }

}
