package uk.co.idv.common.adapter.json.error.badrequest.header;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.error.ApiError;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelIdHeaderNotProvidedErrorTest {

    private final ApiError error = new ChannelIdHeaderNotProvidedError();

    @Test
    void shouldReturnStatus() {
        assertThat(error.getStatus()).isEqualTo(400);
    }

    @Test
    void shouldReturnTitle() {
        assertThat(error.getTitle()).isEqualTo("Bad request");
    }

    @Test
    void shouldReturnMessage() {
        assertThat(error.getMessage()).isEqualTo("mandatory channel-id header not provided");
    }

    @Test
    void shouldReturnMeta() {
        assertThat(error.getMeta()).isEmpty();
    }

}
