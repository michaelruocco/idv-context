
package uk.co.idv.method.adapter.json.otp.policy.delivery.phone.simswap;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class SimSwapConfigJsonMother {

    private SimSwapConfigJsonMother() {
        // utility class
    }

    public static String build() {
        return loadContentFromClasspath(toPath("sim-swap-config.json"));
    }

    public static String withoutMinDays() {
        return loadContentFromClasspath(toPath("sim-swap-config-without-min-days.json"));
    }

    private static String toPath(String filename) {
        return String.format("policy/delivery/phone/sim-swap/%s", filename);
    }

}
