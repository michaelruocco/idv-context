package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.policy.sequence.stage.StagePolicyModule;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class InvalidStageTypeDeserializationTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new StagePolicyModule());
    private static final String JSON = InvalidStageTypeJsonMother.invalid();

    @Test
    void shouldThrowErrorOnDeserialize() {
        Throwable error = catchThrowable(() -> MAPPER.readValue(JSON, StageType.class));

        assertThat(error)
                .isInstanceOf(InvalidStageTypeException.class)
                .hasMessage("invalid");
    }

}
