package uk.co.idv.method.adapter.json.otp;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface PasscodeConfigJsonMother {

    static String build() {
        return loadContentFromClasspath("passcode-config.json");
    }

}
