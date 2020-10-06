
package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class OtpPhoneNumberConfigJsonMother {

    private OtpPhoneNumberConfigJsonMother() {
        // utility class
    }

    public static String build() {
        return loadContentFromClasspath(toPath("phone-number-config.json"));
    }

    public static String withoutSimSwapConfig() {
        return loadContentFromClasspath(toPath("phone-number-config-without-sim-swap.json"));
    }

    private static String toPath(String filename) {
        return String.format("policy/delivery/phone/%s", filename);
    }

}
