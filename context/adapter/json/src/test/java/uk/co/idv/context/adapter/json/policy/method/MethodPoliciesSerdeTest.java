package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.context.entities.policy.method.MethodPoliciesMother;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class MethodPoliciesSerdeTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new MethodPolicyModule(MAPPING));
    private static final MethodPolicies POLICIES = MethodPoliciesMother.build();
    private static final String JSON = MethodPoliciesJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICIES);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        MethodPolicies policies = MAPPER.readValue(JSON, MethodPolicies.class);

        assertThat(policies).isEqualTo(POLICIES);
    }

}
