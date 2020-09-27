package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibilityMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AsyncFutureSimSwapEligibilitySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
            .registerModule(new SimSwapEligibilityModule());
    private static final Eligibility ELIGIBILITY = AsyncSimSwapEligibilityMother.ineligible();
    private static final String JSON = AsyncSimSwapEligibilityJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ELIGIBILITY);

        assertThatJson(json).isEqualTo(JSON);
    }

}
