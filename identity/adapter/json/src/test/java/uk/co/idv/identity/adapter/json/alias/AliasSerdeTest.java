package uk.co.idv.identity.adapter.json.alias;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.identity.entities.alias.Alias;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class AliasSerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new AliasModule());

    @ParameterizedTest(name = "should serialize alias {1}")
    @ArgumentsSource(AliasArgumentsProvider.class)
    void shouldSerialize(String expectedJson, Alias alias) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(alias);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize alias {1}")
    @ArgumentsSource(AliasArgumentsProvider.class)
    void shouldDeserialize(String json, Alias expectedAlias) throws JsonProcessingException {
        Alias alias = MAPPER.readValue(json, Alias.class);

        assertThat(alias).isEqualTo(expectedAlias);
    }

}
