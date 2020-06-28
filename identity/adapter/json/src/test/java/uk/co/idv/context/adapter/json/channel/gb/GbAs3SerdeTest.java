package uk.co.idv.context.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.channel.ChannelModule;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.gb.GbAs3Mother;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class GbAs3SerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new ChannelModule());
    private static final String JSON = ContentLoader.loadContentFromClasspath("channel/gb/as3.json");
    private static final Channel CHANNEL = GbAs3Mother.as3();

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
