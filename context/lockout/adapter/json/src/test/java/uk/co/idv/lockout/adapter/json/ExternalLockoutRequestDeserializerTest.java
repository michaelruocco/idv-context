package uk.co.idv.lockout.adapter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.lockout.entities.ClockMother;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequestMother;
import uk.co.idv.lockout.entities.ExternalLockoutRequest;

import java.time.Clock;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalLockoutRequestDeserializerTest {

    private static final Clock CLOCK = ClockMother.build();
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutParentModule(CLOCK));

    @Test
    void shouldDeserializeRequest() throws JsonProcessingException {
        String json = ExternalLockoutRequestJsonMother.build();

        ExternalLockoutRequest request = MAPPER.readValue(json, ExternalLockoutRequest.class);

        assertThat(request).isEqualTo(DefaultExternalLockoutRequestMother.build());
    }

}
