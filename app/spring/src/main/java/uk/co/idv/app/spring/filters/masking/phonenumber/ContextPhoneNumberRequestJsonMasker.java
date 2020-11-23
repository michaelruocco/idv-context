package uk.co.idv.app.spring.filters.masking.phonenumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class ContextPhoneNumberRequestJsonMasker extends PhoneNumberJsonMasker {

    public ContextPhoneNumberRequestJsonMasker(ObjectMapper mapper) {
        super(mapper, paths());
    }

    private static Collection<JsonPath> paths() {
        return JsonPathFactory.toJsonPaths("$.channel.phoneNumbers[*].value");
    }


}
