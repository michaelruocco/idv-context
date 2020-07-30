package uk.co.idv.context.adapter.json.policy.key.channelactivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.policy.key.PolicyKeyModule;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKeyMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ChannelActivityPolicyKeySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PolicyKeyModule());
    private static final String JSON = ChannelActivityPolicyKeyJsonMother.defaultChannelActivityKey();
    private static final PolicyKey KEY = ChannelActivityPolicyKeyMother.defaultChannelActivityKey();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(KEY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        PolicyKey key = MAPPER.readValue(JSON, PolicyKey.class);

        assertThat(key).isEqualTo(KEY);
    }

}
