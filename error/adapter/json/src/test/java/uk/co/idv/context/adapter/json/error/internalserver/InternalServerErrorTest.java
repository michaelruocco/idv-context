package uk.co.idv.context.adapter.json.error.internalserver;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class InternalServerErrorTest {

    private static final String MESSAGE = "my-message";

    private final ApiError error = new InternalServerError(MESSAGE);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(500);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Internal server error");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo(MESSAGE);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
