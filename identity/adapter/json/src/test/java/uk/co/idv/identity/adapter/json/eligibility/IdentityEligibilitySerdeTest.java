package uk.co.idv.identity.adapter.json.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.eligibility.IdentityEligibilityMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class IdentityEligibilitySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new IdentityEligibilityModule())
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final String JSON = EligibilityJsonMother.build();
    private static final IdentityEligibility ELIGIBILITY = IdentityEligibilityMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ELIGIBILITY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        IdentityEligibility eligibility = MAPPER.readValue(JSON, IdentityEligibility.class);

        assertThat(eligibility).isEqualTo(ELIGIBILITY);
    }

}
