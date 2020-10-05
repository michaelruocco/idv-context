package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

public interface AcceptableSimSwapStatusesMother {

    static AcceptableSimSwapStatuses onlySuccess() {
        return new AcceptableSimSwapStatuses(AcceptableSimSwapStatuses.SUCCESS);
    }

}
