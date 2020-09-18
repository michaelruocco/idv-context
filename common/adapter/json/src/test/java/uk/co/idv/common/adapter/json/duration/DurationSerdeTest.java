package uk.co.idv.common.adapter.json.duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class DurationSerdeTest {

    private static final Duration DURATION = Duration.ofMinutes(5);
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new DurationModule());
    private static final String JSON = loadContentFromClasspath("duration/duration.json");

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(DURATION);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Duration duration = MAPPER.readValue(JSON, Duration.class);

        assertThat(duration).isEqualTo(DURATION);
    }

}
