package uk.co.idv.method.adpter.json.push;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.adapter.json.push.PushNotificationJsonMother;
import uk.co.idv.method.adapter.json.push.PushNotificationModule;
import uk.co.idv.method.entities.push.PushNotification;
import uk.co.idv.method.entities.push.PushNotificationMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new PushNotificationModule());
    private static final PushNotification PUSH_NOTIFICATION = PushNotificationMother.build();
    private static final String JSON = PushNotificationJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(PUSH_NOTIFICATION);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        PushNotification pushNotification = MAPPER.readValue(JSON, PushNotification.class);

        assertThat(pushNotification).isEqualTo(PUSH_NOTIFICATION);
    }

}
