package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.policy.sequence.stage.StagePolicyModule;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class StageTypeSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new StagePolicyModule());

    @ParameterizedTest(name = "should serialize stage type  {1}")
    @ArgumentsSource(StageTypeArgumentsProvider.class)
    void shouldSerialize(String expectedJson, StageType type) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(type);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize stage type {1}")
    @ArgumentsSource(StageTypeArgumentsProvider.class)
    void shouldDeserialize(String json, StageType expectedType) throws JsonProcessingException {
        StageType type = MAPPER.readValue(json, StageType.class);

        assertThat(type).isEqualTo(expectedType);
    }

}
