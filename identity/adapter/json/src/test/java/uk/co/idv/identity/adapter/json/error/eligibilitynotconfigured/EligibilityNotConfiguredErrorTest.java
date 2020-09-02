package uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class EligibilityNotConfiguredErrorTest {

    private static final String CHANNEL_ID = "my-channel";

    private final ApiError error = new EligibilityNotConfiguredError(CHANNEL_ID);

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(422);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Eligibility not configured");
    }

    @Test
    void shouldReturnMessage() {
        String expectedMessage = String.format("eligibility not configured for channel %s", CHANNEL_ID);

        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).contains(
                entry("channelId", CHANNEL_ID)
        );
    }

}
