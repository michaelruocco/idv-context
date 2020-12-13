package uk.co.idv.identity.adapter.json.identity.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.identity.adapter.json.emailaddress.mask.EmailAddressJsonMasker;
import uk.co.idv.identity.adapter.json.phonenumber.mask.PhoneNumberJsonMasker;
import uk.co.mruoc.json.mask.CompositeJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class IdentityJsonMasker extends CompositeJsonMasker {

    public IdentityJsonMasker(ObjectMapper mapper) {
        super(
                new IdentityEmailAddressJsonMasker(mapper),
                new IdentityPhoneNumberJsonMasker(mapper)
        );
    }

    private static class IdentityEmailAddressJsonMasker extends EmailAddressJsonMasker {

        public IdentityEmailAddressJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths("$.emailAddresses[*]");
        }

    }

    private static class IdentityPhoneNumberJsonMasker extends PhoneNumberJsonMasker {

        public IdentityPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, buildPaths());
        }

        private static Collection<JsonPath> buildPaths() {
            return JsonPathFactory.toJsonPaths("$.phoneNumbers[*].value");
        }

    }

}
