package uk.co.idv.context.adapter.json.alias.idvid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.adapter.json.alias.AliasModule;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class AliasesSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModules(new AliasModule());
    private static final String JSON = ContentLoader.loadContentFromClasspath("alias/aliases.json");
    private static final Aliases ALIASES = AliasesMother.with(DefaultAliasMother.build());

    @Test
    void shouldSerialize() throws JsonProcessingException {
        final String json = MAPPER.writeValueAsString(ALIASES);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        final Aliases aliases = MAPPER.readValue(JSON, Aliases.class);

        assertThat(aliases).isEqualTo(ALIASES);
    }

}
