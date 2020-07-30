package uk.co.idv.context.adapter.json.policy.key.channel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.policy.key.PolicyKeyModule;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ChannelPolicyKeySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PolicyKeyModule());
    private static final String JSON = ChannelPolicyKeyJsonMother.defaultChannelKey();
    private static final PolicyKey KEY = ChannelPolicyKeyMother.defaultChannelKey();

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
