package uk.co.idv.app.spring.filters.masking.phonenumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class ContextPhoneNumberResponseJsonMasker extends PhoneNumberJsonMasker {

    public ContextPhoneNumberResponseJsonMasker(ObjectMapper mapper) {
        super(mapper, paths());
    }

    private static Collection<JsonPath> paths() {
        return JsonPathFactory.toJsonPaths(
                "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='sms')].value",
                "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='voice')].value",
                "$.request.identity.phoneNumbers[*].value"
        );
    }

}
