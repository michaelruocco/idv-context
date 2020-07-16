package uk.co.idv.context.adapter.json.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.context.entities.channel.Channel;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ChannelSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new ChannelModule());

    @ParameterizedTest(name = "should serialize channel {1}")
    @ArgumentsSource(ChannelArgumentsProvider.class)
    void shouldSerialize(String expectedJson, Channel channel) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(channel);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize channel {1}")
    @ArgumentsSource(ChannelArgumentsProvider.class)
    void shouldDeserialize(String json, Channel expectedChannel) throws JsonProcessingException {
        Channel channel = MAPPER.readValue(json, Channel.class);

        assertThat(channel).isEqualTo(expectedChannel);
    }

}
