package uk.co.idv.context.adapter.json.context.eligibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class EligibilitySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new EligibilityModule());

    @ParameterizedTest(name = "should serialize eligibility {1}")
    @ArgumentsSource(EligibilityArgumentsProvider.class)
    void shouldSerialize(String expectedJson, Eligibility eligibility) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(eligibility);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize eligibility {1}")
    @ArgumentsSource(EligibilityArgumentsProvider.class)
    void shouldDeserialize(String json, Eligibility expectedEligibility) throws JsonProcessingException {
        Eligibility eligibility = MAPPER.readValue(json, Eligibility.class);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

}
