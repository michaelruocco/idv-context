package uk.co.idv.context.adapter.json.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.ContextPolicyMother;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ContextPolicySerdeTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new ContextPolicyModule(MAPPING));
    private static final ContextPolicy POLICY = ContextPolicyMother.build();
    private static final String JSON = ContextPolicyJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        ContextPolicy policy = MAPPER.readValue(JSON, ContextPolicy.class);

        assertThat(policy).isEqualTo(POLICY);
    }

}
