package uk.co.idv.context.adapter.json.context.method.fake;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpJsonMother {

    static String build() {
        return loadContentFromClasspath("context/method/otp/otp.json");
    }

}
