package uk.co.idv.method.entities.otp.policy.delivery.phone.simswap;

import uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses;

public interface AcceptableSimSwapStatusesMother {

    static AcceptableSimSwapStatuses onlySuccess() {
        return new AcceptableSimSwapStatuses(AcceptableSimSwapStatuses.SUCCESS);
    }

}
