package uk.co.idv.context.adapter.json.context.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.result.Results;
import uk.co.idv.context.entities.context.result.ResultsMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ResultsSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new ResultModule());

    private static final Results RESULTS = ResultsMother.build();
    private static final String JSON = ResultsJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(RESULTS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Results results = MAPPER.readValue(JSON, Results.class);

        assertThat(results).isEqualTo(RESULTS);
    }

}
