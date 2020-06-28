package uk.co.idv.context.adapter.json.phonenumber;

import uk.co.mruoc.file.content.ContentLoader;

public interface PhoneNumbersJsonMother {

    static String mobileAndOther() {
        return ContentLoader.loadContentFromClasspath("phonenumber/phone-numbers.json");
    }

}
