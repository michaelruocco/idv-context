package uk.co.idv.context.adapter.json.policy.sequence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.method.MethodMapping;
import uk.co.idv.context.adapter.json.context.method.fake.FakeMethodMapping;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePoliciesMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class SequencePoliciesSerdeTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new SequencePolicyModule(MAPPING));
    private static final SequencePolicies POLICIES = SequencePoliciesMother.build();
    private static final String JSON = SequencePoliciesJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICIES);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        SequencePolicies policies = MAPPER.readValue(JSON, SequencePolicies.class);

        assertThat(policies).isEqualTo(POLICIES);
    }

}
