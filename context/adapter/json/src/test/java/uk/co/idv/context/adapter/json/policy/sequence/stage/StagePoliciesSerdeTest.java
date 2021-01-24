package uk.co.idv.context.adapter.json.policy.sequence.stage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class StagePoliciesSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new StagePolicyModule(new FakeMethodMapping()));

    @ParameterizedTest(name = "should serialize stage policies  {1}")
    @ArgumentsSource(StagePoliciesArgumentsProvider.class)
    void shouldSerialize(String expectedJson, StagePolicies policies) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(policies);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize stage policies {1}")
    @ArgumentsSource(StagePoliciesArgumentsProvider.class)
    void shouldDeserialize(String json, StagePolicies expectedPolicies) throws JsonProcessingException {
        StagePolicies policies = MAPPER.readValue(json, StagePolicies.class);

        assertThat(policies).isEqualTo(expectedPolicies);
    }

}
