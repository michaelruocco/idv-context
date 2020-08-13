package uk.co.idv.context.adapter.json.error.policynotfound;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class PolicyNotFoundErrorTest {

    private static final String MESSAGE = "my-message";

    private final ApiError error = new PolicyNotFoundError(MESSAGE);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(404);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Policy not found");
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
