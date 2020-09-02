package uk.co.idv.identity.adapter.json.error.identitynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityNotFoundErrorTest {

    private static final Alias ALIAS = IdvIdMother.idvId();

    private final ApiError error = new IdentityNotFoundError(AliasesMother.with(ALIAS));

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(404);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Identity not found");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo(ALIAS.format());
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
