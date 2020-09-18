
package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap;

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
        return String.format("method/otp/delivery/phone/sim-swap/%s", filename);
    }

}
