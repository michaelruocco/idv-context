package uk.co.idv.context.adapter.json.emailaddress;

import uk.co.mruoc.file.content.ContentLoader;

public interface EmailAddressesJsonMother {

    static String two() {
        return ContentLoader.loadContentFromClasspath("emailaddress/email-addresses.json");
    }

}
