
package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

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
        return String.format("policy/method/otp/delivery/phone/%s", filename);
    }

}
