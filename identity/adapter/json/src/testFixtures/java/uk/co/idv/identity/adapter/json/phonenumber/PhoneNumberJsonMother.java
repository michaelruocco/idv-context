package uk.co.idv.identity.adapter.json.phonenumber;

import uk.co.mruoc.file.content.ContentLoader;

public interface PhoneNumberJsonMother {

    static String build() {
        return ContentLoader.loadContentFromClasspath("phonenumber/phone-number.json");
    }

}
