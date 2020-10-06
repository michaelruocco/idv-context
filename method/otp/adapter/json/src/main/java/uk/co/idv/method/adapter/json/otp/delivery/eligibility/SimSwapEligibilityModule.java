package uk.co.idv.method.adapter.json.otp.delivery.eligibility;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.method.entities.otp.simswap.eligibility.AsyncFutureSimSwapEligibility;
import uk.co.idv.method.entities.otp.simswap.eligibility.SimSwapEligibility;

import java.util.Collections;

public class SimSwapEligibilityModule extends SimpleModule {

    public SimSwapEligibilityModule() {
        super("sim-swap-eligibility-module", Version.unknownVersion());

        setMixInAnnotation(SimSwapEligibility.class, SimSwapEligibilityMixin.class);
        setMixInAnnotation(AsyncFutureSimSwapEligibility.class, AsyncSimSwapEligibilityMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new Jdk8Module());
    }

}
