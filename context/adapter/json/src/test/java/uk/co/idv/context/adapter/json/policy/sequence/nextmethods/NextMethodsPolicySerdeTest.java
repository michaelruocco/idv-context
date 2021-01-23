package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.context.sequence.nextmethods.NextMethodsPolicy;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class NextMethodsPolicySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new NextMethodsPolicyModule());

    @ParameterizedTest(name = "should serialize next methods policy  {1}")
    @ArgumentsSource(NextMethodsPolicyArgumentsProvider.class)
    void shouldSerialize(String expectedJson, NextMethodsPolicy policy) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(policy);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize next methods policy {1}")
    @ArgumentsSource(NextMethodsPolicyArgumentsProvider.class)
    void shouldDeserialize(String json, NextMethodsPolicy expectedPolicy) throws JsonProcessingException {
        NextMethodsPolicy policy = MAPPER.readValue(json, NextMethodsPolicy.class);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

}
