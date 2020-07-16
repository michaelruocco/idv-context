package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequestMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class CreateEligibilityRequestSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new EligibilityModule());
    private static final String JSON = CreateEligibilityRequestJsonMother.build();
    private static final CreateEligibilityRequest REQUEST = CreateEligibilityRequestMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(REQUEST);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        CreateEligibilityRequest request = MAPPER.readValue(JSON, CreateEligibilityRequest.class);

        assertThat(request).isEqualTo(REQUEST);
    }

}
