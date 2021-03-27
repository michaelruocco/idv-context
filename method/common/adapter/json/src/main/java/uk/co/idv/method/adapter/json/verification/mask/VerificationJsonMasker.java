package uk.co.idv.method.adapter.json.verification.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.CompositeJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.json.mask.email.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.phone.PhoneNumberJsonMasker;

import java.util.Collection;

public class VerificationJsonMasker extends CompositeJsonMasker {

    public VerificationJsonMasker(ObjectMapper mapper) {
        super(
                new VerificationEmailAddressJsonMasker(mapper),
                new VerificationPhoneNumberJsonMasker(mapper)
        );
    }

    private static class VerificationEmailAddressJsonMasker extends EmailAddressJsonMasker {

        public VerificationEmailAddressJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths(
                    "$.methods[*].deliveryMethods[?(@.type=='email')].value"
            );
        }

    }

    private static class VerificationPhoneNumberJsonMasker extends PhoneNumberJsonMasker {

        public VerificationPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths(
                    "$.methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='sms')].value",
                    "$.methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='voice')].value"
            );
        }

    }

}
