package uk.co.idv.method.adapter.json.otp.delivery.eligibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.simswap.AsyncSimSwapEligibilityMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AsyncFutureSimSwapEligibilitySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new SimSwapEligibilityModule());
    private static final Eligibility ELIGIBILITY = AsyncSimSwapEligibilityMother.ineligible();
    private static final String JSON = AsyncSimSwapEligibilityJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ELIGIBILITY);

        assertThatJson(json).isEqualTo(JSON);
    }

}
