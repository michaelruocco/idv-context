package uk.co.idv.context.adapter.json.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.Policies;
import uk.co.idv.context.entities.policy.PoliciesMother;
import uk.co.idv.context.entities.policy.Policy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class PoliciesSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new PolicyModule());
    private static final Policies<Policy> POLICIES = PoliciesMother.singleFakePolicy();
    private static final String JSON = PoliciesJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICIES);

        assertThatJson(json).isEqualTo(JSON);
    }

}
