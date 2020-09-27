package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SimSwapEligibilityJsonMother {

    static String build() {
        return loadContentFromClasspath("context/method/otp/delivery/eligibility/sim-swap-eligibility.json");
    }

}
