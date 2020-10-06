package uk.co.idv.method.adapter.json.otp.delivery.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AsyncSimSwapEligibilityJsonMother {

    static String build() {
        return loadContentFromClasspath("delivery/eligibility/async-sim-swap-eligibility.json");
    }

}
