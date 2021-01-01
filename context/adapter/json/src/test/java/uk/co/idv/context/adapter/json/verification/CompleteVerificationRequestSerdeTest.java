package uk.co.idv.context.adapter.json.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.entities.verification.CompleteVerificationRequest;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class CompleteVerificationRequestSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new VerificationModule());

    @ParameterizedTest(name = "should serialize complete verification request {1}")
    @ArgumentsSource(CompleteVerificationRequestArgumentsProvider.class)
    void shouldSerialize(String expectedJson, CompleteVerificationRequest request) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(request);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize complete verification request {1}")
    @ArgumentsSource(CompleteVerificationRequestArgumentsProvider.class)
    void shouldDeserialize(String json, CompleteVerificationRequest expectedRequest) throws JsonProcessingException {
        CompleteVerificationRequest request = MAPPER.readValue(json, CompleteVerificationRequest.class);

        assertThat(request).isEqualTo(expectedRequest);
    }

}
