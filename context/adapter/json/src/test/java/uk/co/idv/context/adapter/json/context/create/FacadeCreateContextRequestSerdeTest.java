package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequestMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class FacadeCreateContextRequestSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new CreateContextModule())
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final CreateContextRequest REQUEST = FacadeCreateContextRequestMother.build();
    private static final String JSON = FacadeCreateContextRequestJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(REQUEST);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        CreateContextRequest request = MAPPER.readValue(JSON, FacadeCreateContextRequest.class);

        assertThat(request).isEqualTo(REQUEST);
    }

}