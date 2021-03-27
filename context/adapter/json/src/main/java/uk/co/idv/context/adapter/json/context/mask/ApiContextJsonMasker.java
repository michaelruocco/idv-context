package uk.co.idv.context.adapter.json.context.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.CompositeJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.json.mask.email.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.phone.PhoneNumberJsonMasker;

import java.util.Collection;

public class ApiContextJsonMasker extends CompositeJsonMasker {

    public ApiContextJsonMasker(ObjectMapper mapper) {
        super(
                new ApiContextEmailAddressJsonMasker(mapper),
                new ApiContextPhoneNumberJsonMasker(mapper)
        );
    }

    private static class ApiContextEmailAddressJsonMasker extends EmailAddressJsonMasker {

        public ApiContextEmailAddressJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths(
                    "$.sequences[*].stages[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='email')].value",
                    "$.channel.emailAddresses[*]"
            );
        }

    }

    private static class ApiContextPhoneNumberJsonMasker extends PhoneNumberJsonMasker {

        public ApiContextPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths(
                    "$.sequences[*].stages[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='sms')].value",
                    "$.sequences[*].stages[*].methods[?(@.name=='one-time-passcode')].deliveryMethods[?(@.type=='voice')].value",
                    "$.channel.phoneNumbers[*].value"
            );
        }

    }

}
