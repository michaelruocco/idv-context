package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequenceMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class SequenceSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new SequenceModule());
    private static final Sequence SEQUENCE = SequenceMother.otpOnly();
    private static final String JSON = SequenceJsonMother.otpOnly();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(SEQUENCE);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Sequence sequence = MAPPER.readValue(JSON, Sequence.class);

        assertThat(sequence).isEqualTo(SEQUENCE);
    }

}
