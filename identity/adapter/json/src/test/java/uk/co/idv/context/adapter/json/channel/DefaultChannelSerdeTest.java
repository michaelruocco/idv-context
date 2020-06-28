package uk.co.idv.context.adapter.json.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultChannelSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new ChannelModule());
    private static final String JSON = ContentLoader.loadContentFromClasspath("channel/default-channel.json");
    private static final Channel CHANNEL = DefaultChannelMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(CHANNEL);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Channel channel = MAPPER.readValue(JSON, Channel.class);

        assertThat(channel).isEqualTo(CHANNEL);
    }

}
