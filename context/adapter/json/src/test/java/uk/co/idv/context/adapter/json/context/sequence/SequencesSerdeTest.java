package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class SequencesSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new SequenceModule());
    private static final Sequences SEQUENCES = SequencesMother.otpOnly();
    private static final String JSON = SequencesJsonMother.otpOnly();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(SEQUENCES);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Sequences sequences = MAPPER.readValue(JSON, Sequences.class);

        assertThat(sequences).isEqualTo(SEQUENCES);
    }

}
