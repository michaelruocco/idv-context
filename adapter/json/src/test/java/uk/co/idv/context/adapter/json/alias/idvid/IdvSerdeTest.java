package uk.co.idv.context.adapter.json.alias.idvid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.alias.AliasModule;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class IdvSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModules(new AliasModule());
    private static final String JSON = ContentLoader.loadContentFromClasspath("alias/idv-id.json");
    private static final Alias ALIAS = IdvIdMother.idvId();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(ALIAS);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        final Alias alias = MAPPER.readValue(JSON, Alias.class);

        assertThat(alias).isEqualTo(ALIAS);
    }

}
