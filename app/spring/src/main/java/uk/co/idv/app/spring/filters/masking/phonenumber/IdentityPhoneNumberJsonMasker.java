package uk.co.idv.app.spring.filters.masking.phonenumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class IdentityPhoneNumberJsonMasker extends PhoneNumberJsonMasker {

    public IdentityPhoneNumberJsonMasker(ObjectMapper mapper) {
        super(mapper, buildPaths());
    }

    private static Collection<JsonPath> buildPaths() {
        return JsonPathFactory.toJsonPaths("$.phoneNumbers[*].value");
    }

}
