package uk.co.idv.method.adapter.json.otp.policy;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/otp-policy.json");
    }

}
