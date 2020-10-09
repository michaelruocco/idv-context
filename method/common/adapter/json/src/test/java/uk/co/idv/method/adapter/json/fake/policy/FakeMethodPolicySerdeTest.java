package uk.co.idv.method.adapter.json.fake.policy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicy;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicyMother;
import uk.co.idv.method.entities.policy.MethodPolicy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class FakeMethodPolicySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new FakeMethodPolicyModule());
    private static final MethodPolicy POLICY = FakeMethodPolicyMother.build();
    private static final String JSON = FakeMethodPolicyJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(POLICY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        FakeMethodPolicy policy = MAPPER.readValue(JSON, FakeMethodPolicy.class);

        assertThat(policy).isEqualTo(POLICY);
    }

}
