package uk.co.idv.context.adapter.json.error.aliastype;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class UnsupportedAliasTypeErrorTest {

    private final String type = "my-type";

    private final ApiError error = new UnsupportedAliasTypeError(type);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Unsupported alias type");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo(type);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
