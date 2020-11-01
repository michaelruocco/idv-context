package uk.co.idv.context.adapter.json.error.contextnotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class ContextNotFoundErrorTest {

    private static final String MESSAGE = "error-message";

    private final ApiError error = new ContextNotFoundError(MESSAGE);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(404);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Context not found");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo(MESSAGE);
    }

    @Test
    void shouldReturnEmptyMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
