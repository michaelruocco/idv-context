package uk.co.idv.context.adapter.json.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.results.FacadeRecordResultRequestJsonMother;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.context.entities.result.FacadeRecordResultRequestMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class FacadeRecordResultRequestSerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new RecordRequestModule());
    private static final FacadeRecordResultRequest REQUEST = FacadeRecordResultRequestMother.build();
    private static final String JSON = FacadeRecordResultRequestJsonMother.build();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(REQUEST);

        assertThatJson(json).isEqualTo(JSON);
    }

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        FacadeRecordResultRequest request = MAPPER.readValue(JSON, FacadeRecordResultRequest.class);

        assertThat(request).isEqualTo(REQUEST);
    }

}
