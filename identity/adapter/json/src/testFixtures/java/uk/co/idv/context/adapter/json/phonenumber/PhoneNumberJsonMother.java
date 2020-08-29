package uk.co.idv.context.adapter.json.phonenumber;

import uk.co.mruoc.file.content.ContentLoader;

public interface PhoneNumberJsonMother {

    static String build() {
        return ContentLoader.loadContentFromClasspath("phonenumber/phone-number.json");
    }

}
