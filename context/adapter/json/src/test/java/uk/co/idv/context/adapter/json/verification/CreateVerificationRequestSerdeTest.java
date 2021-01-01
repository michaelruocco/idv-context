package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.verification.CreateVerificationRequest;
import uk.co.idv.context.entities.verification.CreateVerificationRequestMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class CreateVerificationRequestSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new VerificationModule());
    private static final CreateVerificationRequest REQUEST = CreateVerificationRequestMother.build();
    private static final String JSON = CreateVerificationRequestJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(REQUEST);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        CreateVerificationRequest request = MAPPER.readValue(JSON, CreateVerificationRequest.class);

        assertThat(request).isEqualTo(REQUEST);
    }

}
