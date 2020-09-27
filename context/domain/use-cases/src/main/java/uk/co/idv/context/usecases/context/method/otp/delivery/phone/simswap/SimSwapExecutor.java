package uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap;

import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.AsyncFutureSimSwapEligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

public interface SimSwapExecutor {

    AsyncFutureSimSwapEligibility executeSimSwap(OtpPhoneNumber number, SimSwapConfig config);

}
