package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.common.usecases.async.Delay;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;

import java.time.Clock;

@Builder
public class StubSimSwapEligibilitySupplierFactory {

    private final Clock clock;
    private final Delay delay;
    private final StubSimSwapResultFactory resultFactory;

    public StubSimSwapEligibilitySupplier toSupplier(OtpPhoneNumber number) {
        return StubSimSwapEligibilitySupplier.builder()
                .clock(clock)
                .resultFactory(resultFactory)
                .delay(delay)
                .number(number)
                .build();
    }

}
