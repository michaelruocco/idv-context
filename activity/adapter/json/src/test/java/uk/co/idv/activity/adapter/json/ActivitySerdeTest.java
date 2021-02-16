package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import uk.co.idv.activity.entities.Activity;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class ActivitySerdeTest {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(new ActivityModule());

    @ParameterizedTest(name = "should serialize activity {1}")
    @ArgumentsSource(ActivityArgumentsProvider.class)
    void shouldSerialize(String expectedJson, Activity activity) throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(activity);

        assertThatJson(json).isEqualTo(expectedJson);
    }

    @ParameterizedTest(name = "should deserialize activity {1}")
    @ArgumentsSource(ActivityArgumentsProvider.class)
    void shouldDeserialize(String json, Activity expectedActivity) throws JsonProcessingException {
        Activity activity = MAPPER.readValue(json, Activity.class);

        assertThat(activity).isEqualTo(expectedActivity);
    }

}
