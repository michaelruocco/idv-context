package uk.co.idv.method.adpter.json.push.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.adapter.json.push.policy.PushNotificationPolicyJsonMother;
import uk.co.idv.method.adapter.json.push.policy.PushNotificationPolicyModule;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicy;
import uk.co.idv.method.entities.push.policy.PushNotificationPolicyMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationPolicySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PushNotificationPolicyModule());
    private static final PushNotificationPolicy POLICY = PushNotificationPolicyMother.build();
    private static final String JSON = PushNotificationPolicyJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        PushNotificationPolicy policy = MAPPER.readValue(JSON, PushNotificationPolicy.class);

        assertThat(policy).isEqualTo(POLICY);
    }

}
