package uk.co.idv.context.adapter.json.lockout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.lockout.DefaultExternalLockoutRequestMother;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;

import static org.assertj.core.api.Assertions.assertThat;

class ExternalLockoutRequestDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new LockoutParentModule());

    @Test
    void shouldDeserializeRequest() throws JsonProcessingException {
        String json = ExternalLockoutRequestJsonMother.build();

        ExternalLockoutRequest request = MAPPER.readValue(json, ExternalLockoutRequest.class);

        assertThat(request).isEqualTo(DefaultExternalLockoutRequestMother.build());
    }

}
