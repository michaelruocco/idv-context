package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.eligibility.EligibilityMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class EligibilitySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new EligibilityModule());
    private static final String JSON = EligibilityJsonMother.build();
    private static final Eligibility ELIGIBILITY = EligibilityMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ELIGIBILITY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Eligibility eligibility = MAPPER.readValue(JSON, Eligibility.class);

        assertThat(eligibility).isEqualTo(ELIGIBILITY);
    }

}
