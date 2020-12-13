package uk.co.idv.identity.adapter.repository.document.emailaddress;

import java.util.Arrays;
import java.util.Collection;

public interface EmailAddressDocumentMother {

    static Collection<String> two() {
        return Arrays.asList(
                "joe.bloggs@hotmail.co.uk",
                "joebloggs@yahoo.co.uk"
        );
    }

}
