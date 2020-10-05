package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap;

import com.fasterxml.jackson.core.Version;
import uk.co.idv.common.adapter.json.AbstractContextModule;
import uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

public class SimSwapConfigModule extends AbstractContextModule {

    public SimSwapConfigModule() {
        super("sim-swap-config-module", Version.unknownVersion());

        setMixInAnnotation(SimSwapConfig.class, SimSwapConfigMixin.class);

        addDeserializer(AcceptableSimSwapStatuses.class, new AcceptableSimSwapStatusesDeserializer());
        addDeserializer(SimSwapConfig.class, new SimSwapConfigDeserializer());
    }

}
