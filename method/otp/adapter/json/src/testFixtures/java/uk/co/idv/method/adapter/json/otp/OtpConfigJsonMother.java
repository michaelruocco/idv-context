package uk.co.idv.method.adapter.json.otp;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpConfigJsonMother {

    static String build() {
        return loadContentFromClasspath("otp-config.json");
    }

}
