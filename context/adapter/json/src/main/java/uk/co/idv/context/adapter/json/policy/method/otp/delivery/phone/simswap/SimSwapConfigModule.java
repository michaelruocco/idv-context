package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.common.adapter.json.duration.DurationModule;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.util.Arrays;

public class SimSwapConfigModule extends SimpleModule {

    public SimSwapConfigModule() {
        super("sim-swap-config-module", Version.unknownVersion());

        addDeserializer(AcceptableSimSwapStatuses.class, new AcceptableSimSwapStatusesDeserializer());
        addDeserializer(SimSwapConfig.class, new SimSwapConfigDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new DurationModule()
        );
    }

}
