package uk.co.idv.method.adapter.json.error.notnextmethod;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodErrorTest {

    private static final String MESSAGE = "error-message";

    private final ApiError error = new NotNextMethodError(MESSAGE);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Not next method");
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
