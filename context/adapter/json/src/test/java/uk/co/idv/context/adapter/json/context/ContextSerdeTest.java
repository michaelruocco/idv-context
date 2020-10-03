package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;
import static com.fasterxml.jackson.databind.SerializationFeature.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ContextSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(WRITE_DATES_AS_TIMESTAMPS)
            .disable(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
            .disable(READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
            .registerModule(new ContextModule());
    private static final Context CONTEXT = ContextMother.build();
    private static final String JSON = ContextJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(CONTEXT);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Context context = MAPPER.readValue(JSON, Context.class);

        assertThat(context).isEqualTo(CONTEXT);
    }

}
