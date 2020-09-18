package uk.co.idv.context.adapter.json.policy.method.otp;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpConfigJsonMother {

    static String build() {
        return loadContentFromClasspath("method/otp/otp-config.json");
    }

}
