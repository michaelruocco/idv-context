package uk.co.idv.identity.adapter.json.emailaddress;

import uk.co.mruoc.file.content.ContentLoader;

public interface EmailAddressesJsonMother {

    static String two() {
        return ContentLoader.loadContentFromClasspath("emailaddress/email-addresses.json");
    }

}
