package uk.co.idv.context.adapter.json.phonenumber;

import uk.co.mruoc.file.content.ContentLoader;

public interface OtherPhoneNumberJsonMother {

    static String other() {
        return ContentLoader.loadContentFromClasspath("phonenumber/other-number.json");
    }

}
