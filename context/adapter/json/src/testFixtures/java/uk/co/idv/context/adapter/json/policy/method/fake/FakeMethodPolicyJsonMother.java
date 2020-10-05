package uk.co.idv.context.adapter.json.policy.method.otp;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface OtpPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/method/otp/otp-policy.json");
    }

}
