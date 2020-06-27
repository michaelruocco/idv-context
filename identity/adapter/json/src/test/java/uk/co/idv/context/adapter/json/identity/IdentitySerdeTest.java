package uk.co.idv.context.adapter.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class IdentitySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new IdentityModule());

    @Test
    void shouldSerialize() throws JsonProcessingException {
        Identity identity = IdentityMother.example();

        String json = MAPPER.writeValueAsString(identity);

        String expectedJson = loadContentFromClasspath("identity/identity.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Identity expectedIdentity = IdentityMother.example();
        String json = loadContentFromClasspath("identity/identity.json");

        Identity identity = MAPPER.readValue(json, Identity.class);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldDeserializeMinimal() throws JsonProcessingException {
        Identity expectedIdentity = IdentityMother.minimal();
        String json = loadContentFromClasspath("identity/minimal-identity.json");

        Identity identity = MAPPER.readValue(json, Identity.class);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

}
