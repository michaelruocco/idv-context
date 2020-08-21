package uk.co.idv.context.adapter.json.lockout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.DefaultRecordAttemptRequestMother;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

import static org.assertj.core.api.Assertions.assertThat;

class RecordAttemptRequestDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutParentModule());

    @Test
    void shouldDeserializeRequest() throws JsonProcessingException {
        String json = RecordAttemptRequestJsonMother.build();

        RecordAttemptRequest request = MAPPER.readValue(json, RecordAttemptRequest.class);

        assertThat(request).isEqualTo(DefaultRecordAttemptRequestMother.build());
    }

}
