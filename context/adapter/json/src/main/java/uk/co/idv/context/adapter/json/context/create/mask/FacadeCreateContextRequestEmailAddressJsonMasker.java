package uk.co.idv.context.adapter.json.context.create.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import uk.co.idv.identity.adapter.json.emailaddress.mask.EmailAddressJsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

public class FacadeCreateContextRequestEmailAddressJsonMasker extends EmailAddressJsonMasker {

    public FacadeCreateContextRequestEmailAddressJsonMasker(ObjectMapper mapper) {
        super(mapper, paths());
    }

    private static Collection<JsonPath> paths() {
        return JsonPathFactory.toJsonPaths("$.channel.emailAddresses[*]");
    }

}
