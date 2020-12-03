package uk.co.idv.identity.adapter.json.identity.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.identity.adapter.json.phonenumber.mask.PhoneNumberJsonMasker;
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
