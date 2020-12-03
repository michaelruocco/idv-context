package uk.co.idv.context.adapter.json.context.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.identity.adapter.json.emailaddress.mask.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class ContextEmailAddressJsonMasker extends EmailAddressJsonMasker {

    public ContextEmailAddressJsonMasker(ObjectMapper mapper) {
        super(mapper, paths());
    }

    private static Collection<JsonPath> paths() {
        return JsonPathFactory.toJsonPaths(
                "$.sequences[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.name=='email')].value",
                "$.request.identity.emailAddresses[*]"
        );
    }

}
