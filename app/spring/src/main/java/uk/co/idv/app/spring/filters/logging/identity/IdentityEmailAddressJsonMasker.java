package uk.co.idv.app.spring.filters.logging.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.app.spring.filters.logging.masking.emailaddress.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class IdentityEmailAddressJsonMasker extends EmailAddressJsonMasker {

    public IdentityEmailAddressJsonMasker(ObjectMapper mapper) {
        super(mapper, buildPaths());
    }

    private static Collection<JsonPath> buildPaths() {
        return JsonPathFactory.toJsonPaths("$.emailAddresses[*]");
    }

}
