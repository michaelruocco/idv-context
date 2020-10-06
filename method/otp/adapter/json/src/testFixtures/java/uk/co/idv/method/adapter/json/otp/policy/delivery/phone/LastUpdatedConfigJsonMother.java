
package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class LastUpdatedConfigJsonMother {

    private LastUpdatedConfigJsonMother() {
        // utility class
    }

    public static String build() {
        return loadContentFromClasspath(toPath("last-updated-config.json"));
    }

    public static String withoutMinDays() {
        return loadContentFromClasspath(toPath("last-updated-config-without-min-days.json"));
    }

    private static String toPath(String filename) {
        return String.format("policy/delivery/phone/%s", filename);
    }

}
