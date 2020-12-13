package uk.co.idv.identity.adapter.json.emailaddress.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathConfig;
import uk.co.mruoc.json.mask.MaskFunction;
import uk.co.mruoc.json.mask.string.IgnoreFirstNAndLastNCharsMasker;

import java.util.Collection;

public class EmailAddressJsonMasker extends JsonMasker {

    public EmailAddressJsonMasker(ObjectMapper mapper, Collection<JsonPath> jsonPaths) {
        super(mapper, jsonPaths, new EmailAddressMaskFunction(), JsonPathConfig.build());
    }

    private static class EmailAddressMaskFunction extends MaskFunction {

        public EmailAddressMaskFunction() {
            super(new IgnoreFirstNAndLastNCharsMasker(4, 6));
        }

    }

}
