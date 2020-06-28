package uk.co.idv.context.adapter.json.phonenumber;

import uk.co.mruoc.file.content.ContentLoader;

public interface MobilePhoneNumberJsonMother {

    static String mobile() {
        return ContentLoader.loadContentFromClasspath("phonenumber/mobile-number.json");
    }

}
