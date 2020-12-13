package uk.co.idv.context.adapter.json.error.contextexpired;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ContextExpiredErrorTest {

    private final UUID id = UUID.fromString("81fa23ed-d2c1-4778-ada6-183101f0e296");
    private final Instant expiry = Instant.parse("2020-11-01T14:34:00.167920Z");

    private final ApiError error = ContextExpiredError.builder()
            .id(id)
            .expiry(expiry)
            .build();

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(410);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Context expired");
    }

    @Test
    void shouldReturnMessage() {
        String expectedMessage = String.format( "context %s expired at %s", id, expiry);

        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).contains(
                entry("id", id.toString()),
                entry("expiry", expiry.toString())
        );
    }

}
