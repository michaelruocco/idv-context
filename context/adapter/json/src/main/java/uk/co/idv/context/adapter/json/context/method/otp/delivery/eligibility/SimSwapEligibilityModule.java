package uk.co.idv.context.adapter.json.context.method.otp.delivery.eligibility;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncSimSwapEligibility;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.SimSwapEligibility;

import java.util.Collections;

public class SimSwapEligibilityModule extends SimpleModule {

    public SimSwapEligibilityModule() {
        super("sim-swap-eligibility-module", Version.unknownVersion());

        setMixInAnnotation(SimSwapEligibility.class, SimSwapEligibilityMixin.class);
        setMixInAnnotation(AsyncSimSwapEligibility.class, AsyncSimSwapEligibilityMixin.class);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new Jdk8Module());
    }

}
