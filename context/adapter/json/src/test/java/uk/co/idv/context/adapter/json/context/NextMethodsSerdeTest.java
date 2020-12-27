package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.context.NextMethods;
import uk.co.idv.context.entities.context.NextMethodsMother;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMapping;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class NextMethodsSerdeTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new ContextModule(MAPPING));
    private static final NextMethods NEXT_METHODS = NextMethodsMother.build();
    private static final String JSON = NextMethodsJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(NEXT_METHODS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        NextMethods context = MAPPER.readValue(JSON, NextMethods.class);

        assertThat(context).isEqualTo(NEXT_METHODS);
    }

}
