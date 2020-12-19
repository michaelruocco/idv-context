package uk.co.idv.identity.adapter.json.phonenumber.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.identity.usecases.protect.mask.PhoneNumberMasker;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathConfig;
import uk.co.mruoc.json.mask.MaskFunction;

import java.util.Collection;

public class PhoneNumberJsonMasker extends JsonMasker {

    public PhoneNumberJsonMasker(ObjectMapper mapper, Collection<JsonPath> jsonPaths) {
        super(mapper, jsonPaths, new PhoneNumberMaskFunction(), JsonPathConfig.build());
    }

    private static class PhoneNumberMaskFunction extends MaskFunction {

        public PhoneNumberMaskFunction() {
            super(new PhoneNumberMasker());
        }

    }

}
