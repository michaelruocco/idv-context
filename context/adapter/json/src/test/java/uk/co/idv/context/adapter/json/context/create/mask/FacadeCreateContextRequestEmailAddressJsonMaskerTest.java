package uk.co.idv.context.adapter.json.context.create.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.context.adapter.json.context.create.CreateContextModule;
import uk.co.idv.context.adapter.json.context.create.FacadeCreateContextRequestJsonMother;
import uk.co.mruoc.json.mask.JsonMasker;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class FacadeCreateContextRequestEmailAddressJsonMaskerTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new CreateContextModule());

    private final JsonMasker masker = new FacadeCreateContextRequestEmailAddressJsonMasker(MAPPER);

    @Test
    void shouldMaskEmailAddressesInRequest() {
        String json = FacadeCreateContextRequestJsonMother.withChannelData();

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(FacadeCreateContextRequestJsonMother.withMaskedChannelEmailAddresses());
    }

}
