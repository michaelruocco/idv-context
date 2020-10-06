package uk.co.idv.method.adapter.json.otp;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpJsonMother {

    static String build() {
        return loadContentFromClasspath("otp.json");
    }

}
