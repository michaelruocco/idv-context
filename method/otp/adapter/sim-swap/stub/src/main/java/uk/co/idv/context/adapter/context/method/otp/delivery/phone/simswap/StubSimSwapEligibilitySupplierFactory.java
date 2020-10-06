package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

import java.time.Clock;

@Builder
public class StubSimSwapEligibilitySupplierFactory {

    private final Clock clock;
    private final Delay delay;
    private final StubSimSwapResultFactory resultFactory;

    public StubSimSwapEligibilitySupplier toSupplier(OtpPhoneNumber number, SimSwapConfig config) {
        return StubSimSwapEligibilitySupplier.builder()
                .clock(clock)
                .resultFactory(resultFactory)
                .delay(delay)
                .config(config)
                .number(number)
                .build();
    }

}
