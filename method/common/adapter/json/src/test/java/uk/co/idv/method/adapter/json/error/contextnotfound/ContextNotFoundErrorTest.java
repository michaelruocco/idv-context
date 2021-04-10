package uk.co.idv.method.adapter.json.error.contextnotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextNotFoundErrorTest {

    private static final UUID ID = UUID.randomUUID();

    private final ApiError error = new ContextNotFoundError(ID);

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
        assertThat(error.getMessage()).isEqualTo(ID.toString());
    }

    @Test
    void shouldReturnEmptyMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
