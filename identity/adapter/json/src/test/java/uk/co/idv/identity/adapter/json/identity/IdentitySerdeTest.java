package uk.co.idv.identity.adapter.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class IdentitySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new IdentityModule());
    private static final String JSON = IdentityJsonMother.example();
    private static final Identity IDENTITY = IdentityMother.example();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(IDENTITY);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Identity identity = MAPPER.readValue(JSON, Identity.class);

        assertThat(identity).isEqualTo(IDENTITY);
    }

}
