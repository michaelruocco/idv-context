package uk.co.idv.context.usecases.context.method.otp.delivery.phone.simswap;

import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;
import uk.co.idv.method.entities.otp.simswap.eligibility.AsyncFutureSimSwapEligibility;

public interface SimSwapExecutor {

    AsyncFutureSimSwapEligibility executeSimSwap(OtpPhoneNumber number, SimSwapConfig config);

}
