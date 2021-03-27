package uk.co.idv.context.adapter.json.context.create.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.mruoc.json.mask.CompositeJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;
import uk.co.mruoc.json.mask.email.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.phone.PhoneNumberJsonMasker;

import java.util.Collection;

public class FacadeCreateContextRequestJsonMasker extends CompositeJsonMasker {

    public FacadeCreateContextRequestJsonMasker(ObjectMapper mapper) {
        super(
                new RequestPhoneNumberJsonMasker(mapper),
                new RequestEmailAddressJsonMasker(mapper)
        );
    }

    private static class RequestPhoneNumberJsonMasker extends PhoneNumberJsonMasker {

        public RequestPhoneNumberJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths("$.channel.phoneNumbers[*].value");
        }

    }

    private static class RequestEmailAddressJsonMasker extends EmailAddressJsonMasker {

        public RequestEmailAddressJsonMasker(ObjectMapper mapper) {
            super(mapper, paths());
        }

        private static Collection<JsonPath> paths() {
            return JsonPathFactory.toJsonPaths("$.channel.emailAddresses[*]");
        }

    }

}
