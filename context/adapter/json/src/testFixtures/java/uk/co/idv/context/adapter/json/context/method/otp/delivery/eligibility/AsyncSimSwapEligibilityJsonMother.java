package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AsyncSimSwapEligibilityJsonMother {

    static String build() {
        return loadContentFromClasspath("context/method/otp/delivery/eligibility/async-sim-swap-eligibility.json");
    }

}
