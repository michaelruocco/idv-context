package uk.co.idv.identity.adapter.json.phonenumber;

import uk.co.mruoc.file.content.ContentLoader;

public interface PhoneNumbersJsonMother {

    static String build() {
        return ContentLoader.loadContentFromClasspath("phonenumber/phone-numbers.json");
    }

}
