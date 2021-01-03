package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.verification.Verification;
import uk.co.idv.method.adapter.json.fake.FakeMethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMapping;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class VerificationSerdeTest {

    private static final MethodMapping MAPPING = new FakeMethodMapping();
    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new VerificationModule(MAPPING));

    @ParameterizedTest(name = "should serialize verification {1}")
    @ArgumentsSource(VerificationArgumentsProvider.class)
    void shouldSerialize(String expectedJson, Verification verification) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(verification);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize verification {1}")
    @ArgumentsSource(VerificationArgumentsProvider.class)
    void shouldDeserialize(String json, Verification expectedVerification) throws JsonProcessingException {
        Verification verification = MAPPER.readValue(json, Verification.class);

        assertThat(verification).isEqualTo(expectedVerification);
    }

}
